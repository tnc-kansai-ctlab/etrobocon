package jp.co.tdc_next.kns.ctlab.tkrobo.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 */
public class RemoteClient extends Frame implements KeyListener {
    private static final long serialVersionUID = 1630664954341554884L;

    private static final String EV3_IPADDR = "10.0.1.1";

    public static final int PORT = 7360;

    public static final int CLOSE = 0;
    public static final int START = 71; // 'g'
    public static final int STOP  = 83; // 's'

    Button btnConnect;
    TextField txtIPAddress;
    TextArea messages;

    private Socket socket;
    private DataOutputStream outStream;

    public RemoteClient(String title, String ip) {
        super(title);

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.ui]" + "[RemoteClient]" + "[RemoteClient]");

        this.setSize(400, 300);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("[Ending Warbird Client]");
                disconnect();
                System.exit(0);
            }
        });
        buildGUI(ip);
        this.setVisible(true);
        btnConnect.addKeyListener(this);
    }

    public static void main(String args[]) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.ui]" + "[RemoteClient]" + "[main]");

        String ip = EV3_IPADDR;
        if (args.length > 0) ip = args[0];
        System.out.println("[Starting Client...]");
        new RemoteClient("LeJOS EV3 Client Sample", ip);
    }

    public void buildGUI(String ip) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.ui]" + "[RemoteClient]" + "[buildGUI]");

        Panel mainPanel = new Panel(new BorderLayout());
        ControlListener cl = new ControlListener();
        btnConnect = new Button("Connect]");
        btnConnect.addActionListener(cl);
        txtIPAddress = new TextField(ip, 16);
        messages = new TextArea("status: DISCONNECTED]");
        messages.setEditable(false);
        Panel north = new Panel(new FlowLayout(FlowLayout.LEFT));
        north.add(btnConnect);
        north.add(txtIPAddress);
        Panel center = new Panel(new GridLayout(5, 1));
        center.add(new Label("G to start, S to stop"));
        Panel center4 = new Panel(new FlowLayout(FlowLayout.LEFT));
        center4.add(messages);
        center.add(center4);
        mainPanel.add(north, "North]");
        mainPanel.add(center, "Center]");
        this.add(mainPanel);
    }

    private void sendCommand(int command) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.ui]" + "[RemoteClient]" + "[sendCommand]");

        messages.setText("status: SENDING command.]");
        try {
            outStream.writeInt(command);
            messages.setText("status: Comand SENT]");
        } catch(IOException io) {
            messages.setText("status: ERROR Probrems occurred sending data.]");
        }
    }

    private class ControlListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

    		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.ui]" + "[ControlListener]" + "[actionPerformed]");

            String command = e.getActionCommand();
            if (command.equals("Connect")) {
                try {
                    socket = new Socket(txtIPAddress.getText(), PORT);
                    outStream = new DataOutputStream(socket.getOutputStream());
                    messages.setText("status: CONNECTED]");
                    btnConnect.setLabel("Disconnect]");
                } catch (Exception exc) {
                    messages.setText("status: FAILURE Error establishing connection with EV3.]");
                    System.out.println("[Error" + exc);
                }
            } else if (command.equals("Disconnect")) {
                disconnect();
            }
        }
    }

    public void disconnect() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.ui]" + "[RemoteClient]" + "[disconnect]");

        try {
            sendCommand(CLOSE);
            socket.close();
            btnConnect.setLabel("Connect]");
            messages.setText("status: DISCONNECTED]");
        } catch (Exception exc) {
            messages.setText("status: FAILURE Error closing connection with EV3.]");
            System.out.println("[Error" + exc);
        }
    }

    public void keyPressed(KeyEvent e) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.ui]" + "[RemoteClient]" + "[keyPressed]");

        sendCommand(e.getKeyCode());
        System.out.println("[Pressed " + e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent arg0) {}
}
