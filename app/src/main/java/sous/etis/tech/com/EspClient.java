package sous.etis.tech.com;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class EspClient extends WebSocketClient {

    public EspClient(URI serverUri) {
        super(serverUri);
    }
    public Handler messageHandler ;
    public EspClient(String url, Handler msgHandler) throws URISyntaxException {
        super(new URI(url));
        messageHandler = msgHandler ;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("SOCKET", handshakedata.getHttpStatusMessage());

        Message msg = Message.obtain();
        msg.what = Constants.SOCKET_ON_OPEN;
        Bundle bundle = new Bundle();
        bundle.putString("message", handshakedata.getHttpStatusMessage());
        msg.setData(bundle);
        messageHandler.sendMessage(msg);
    }

    @Override
    public void onMessage(String message) {
        Log.d("SOCKET",message );
        Message msg = Message.obtain();
        msg.what = Constants.SOCKET_ON_MESSAGE ;
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        msg.setData(bundle);
        messageHandler.sendMessage(msg);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("SOCKET",reason );
        Message msg = Message.obtain();
        msg.what = Constants.SOCKET_ON_CLOSE;
        Bundle bundle = new Bundle();
        bundle.putString("message", reason);
        msg.setData(bundle);
        messageHandler.sendMessage(msg);
    }

    @Override
    public void onError(Exception ex) {
       Log.d("SOCKET", ex.getMessage());
        Log.d("SOCKET",ex.getMessage() );
        Message msg = Message.obtain();
        msg.what = Constants.SOCKET_ON_ERROR;
        Bundle bundle = new Bundle();
        bundle.putString("message", ex.getMessage());
        msg.setData(bundle);
        messageHandler.sendMessage(msg);
    }
}
