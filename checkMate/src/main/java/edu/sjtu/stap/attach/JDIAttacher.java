package edu.sjtu.stap.attach;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.Connector.StringArgument;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import sun.jvm.hotspot.jdi.SADebugServerAttachingConnector;

public class JDIAttacher {

	public static void main(String[] args) throws IOException,
			IllegalConnectorArgumentsException {
		AttachingConnector connector = (SADebugServerAttachingConnector)getConnector();
		Map<String, Argument> arguments = connector.defaultArguments();
		Connector.StringArgument hostName = (StringArgument) arguments
				.get("hostname");
		hostName.setValue("localhost");
		VirtualMachine vm = connector.attach(arguments);
		System.out.println(vm.name());
	}

	private static AttachingConnector getConnector() {
		List<AttachingConnector> connectors = Bootstrap.virtualMachineManager()
				.attachingConnectors();
		Iterator<AttachingConnector> iter = connectors.iterator();
		while (iter.hasNext()) {
			Connector connector = (Connector) iter.next();
			if (connector.name().equals("com.sun.jdi.ProcessAttach")) {
				return (AttachingConnector) connector;
			}
		}
		return null;
	}

}
