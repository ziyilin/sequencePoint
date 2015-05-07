package edu.sjtu.stap.checkmate.spdebugger;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.connect.*;

import java.util.Map;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Yilei
 *
 */
public class Debugger {

    // Running remote VM
    private final VirtualMachine vm;

    // Thread transferring remote output stream to our output stream
    private Thread outThread = null;

    // Mode for tracing the Trace program
    private int debugTraceMode = 0; // VirtualMachine.TRACE_ALL;

    // Class patterns for which we don't want events
    private String[] excludes = {"java.*", "javax.*", "sun.*", "com.sun.*"};

    // Application Name for debugging
    private String applicationName;
    
    public static void main(String[] args) {
        new Debugger(args);
    }

    /**
     * -output <File> <mainClass>
     * Launch target VM.
     */
    Debugger(String[] args) {
        PrintWriter writer = new PrintWriter(System.out);
        int inx;
        for (inx = 0; inx < args.length; ++inx) {
            String arg = args[inx];
            if (arg.charAt(0) != '-') {
                break;
            }
            if (arg.equals("-output")) {
                try {
                    writer = new PrintWriter(new FileWriter(args[++inx]));
                } catch (IOException exc) {
                    System.err.println("Cannot open output file: " + args[inx]
                                       + " - " +  exc);
                    System.exit(1);
                }
            }
        }
        if (inx >= args.length) {
            System.err.println("<class> missing");
            System.exit(1);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(args[inx]);
        applicationName = args[inx];
        for (++inx; inx < args.length; ++inx) {
            sb.append(' ');
            sb.append(args[inx]);
        }
        vm = launchTarget(sb.toString());
        generateTrace(writer);
    }

    void generateTrace(PrintWriter writer) {
        vm.setDebugTraceMode(debugTraceMode);
        EventThread eventThread = new EventThread(vm, excludes, writer, applicationName);

        eventThread.start();
        redirectOutput();
        vm.resume();

        // Shutdown begins when event thread terminates
        try {
            eventThread.join();
            outThread.join(); // before we exit
        } catch (InterruptedException exc) {
            // we don't interrupt
        }
        writer.close();
    }

    /**
     * Launch target VM.
     * Forward target's output and error.
     */
    VirtualMachine launchTarget(String mainArgs) {
        LaunchingConnector connector = findLaunchingConnector();
        Map<String, Connector.Argument> arguments =
           connectorArguments(connector, mainArgs);
        try {
            return connector.launch(arguments);
        } catch (IOException exc) {
            throw new Error("Unable to launch target VM: " + exc);
        } catch (IllegalConnectorArgumentsException exc) {
            throw new Error("Internal error: " + exc);
        } catch (VMStartException exc) {
            throw new Error("Target VM failed to initialize: " +
                            exc.getMessage());
        }
    }

    void redirectOutput() {
        Process process = vm.process();

        // Copy target's output and error to our output and error.
        outThread = new StreamRedirectThread("output reader",
                                             process.getInputStream(),
                                             System.out);
        outThread.start();
    }

    /**
     * Find a com.sun.jdi.CommandLineLaunch connector
     */
    LaunchingConnector findLaunchingConnector() {
        List<LaunchingConnector> connectors = Bootstrap.virtualMachineManager().launchingConnectors();
        for (LaunchingConnector connector : connectors) {
            if (connector.name().equals("com.sun.jdi.CommandLineLaunch")) {
                return connector;
            }
        }
        throw new Error("No launching connector");
    }

    /**
     * Return the launching connector's arguments.
     */
    Map<String, Connector.Argument> connectorArguments(LaunchingConnector connector, String mainArgs) {
        Map<String, Connector.Argument> arguments = connector.defaultArguments();
        Connector.Argument mainArg =
                           (Connector.Argument)arguments.get("main");
        
        // Specify the class path. Currently, it only can be default package.
        Connector.Argument options =(Connector.Argument)arguments.get("options");
        StringBuffer sb = new StringBuffer(System.getProperty("user.dir"));
        String fileSeperator = System.getProperty("file.separator");
        sb = sb.append(fileSeperator).append("target").append(fileSeperator).append("test-classes");
        options.setValue("-cp \"" + sb.toString() + "\"");
//        options.setValue("-D -Djava.system.class.loader=edu.sjtu.stap.checkmate.loadclass.CustomClassLoader");

        if (mainArg == null) {
            throw new Error("Bad launching connector");
        }
        mainArg.setValue(mainArgs);

        return arguments;
    }
}
