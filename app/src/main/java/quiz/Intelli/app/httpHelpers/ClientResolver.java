package quiz.Intelli.app.httpHelpers;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/**
 * Created by Dan on 4/18/2015.
 */
public class ClientResolver
{
    private static HttpClient client;
    private static BasicHttpParams params;
    private static SingleClientConnManager manager;
    private static final int connectionTimeout = 9000; // nine seconds
    private static final int serverTimeout = 9000;
    private static SchemeRegistry registry;

    public static HttpClient getHttpClient(boolean secure)
    {
        registry = new SchemeRegistry();
        params = new BasicHttpParams();
       if(secure)
       {
           registry.register(new Scheme("https", (SocketFactory)SSLSocketFactory.getSocketFactory(), 443));
           HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
           HttpConnectionParams.setSoTimeout(params, serverTimeout);
           manager = new SingleClientConnManager(params, registry);
           client = new DefaultHttpClient(manager, params);
       }
       else
       {
           registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
           HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
           HttpConnectionParams.setSoTimeout(params, serverTimeout);
           manager = new SingleClientConnManager(params, registry);
           client = new DefaultHttpClient(manager, params);
       }
        return client;
    }
}
