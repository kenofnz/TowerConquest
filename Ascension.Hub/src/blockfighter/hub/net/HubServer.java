package blockfighter.hub.net;

import blockfighter.shared.ServerInfo;
import blockfighter.shared.Globals;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;

public class HubServer {

    private Server server;

    public void start() {
        try {
            this.server = new Server();
            Kryo kyro = this.server.getKryo();

            kyro.register(byte[].class);
            kyro.register(ServerInfo.class);
            kyro.register(ServerInfo[].class);

            this.server.addListener(new Listener.ThreadedListener(new HubReceiver()));
            server.bind((Integer) Globals.ServerConfig.HUB_SERVER_TCP_PORT.getValue());
            Globals.log(HubServer.class, "Hub Server listening on port TCP: " + (Integer) Globals.ServerConfig.HUB_SERVER_TCP_PORT.getValue(), Globals.LOG_TYPE_DATA);
            server.start();
        } catch (IOException ex) {
            Globals.logError(ex.toString(), ex);
            System.exit(1);
        }
    }

    public void shutdown() {
        server.stop();
    }

}
