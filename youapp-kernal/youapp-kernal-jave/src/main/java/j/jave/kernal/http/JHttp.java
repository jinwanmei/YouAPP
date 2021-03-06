package j.jave.kernal.http;

import j.jave.kernal.jave.utils.JStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;



/**
 * Super class target for the Apache HTTP client  4.3.6. 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class JHttp <T extends JHttp<T>> extends JHttpBase<T>{

	/**
	 * execute with passed url . 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public Object execute(String url) throws IOException{
		setUrl(url);
		HttpClientBuilder httpClientBuilder = getHttpClientBuilder();
		CloseableHttpClient closeableHttpClient= httpClientBuilder.build();
		
		// get special http request 
		HttpRequestBase httpUriRequest= getHttpType();
		
		RequestConfig requestConfig = 
				RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(timeout).build();
		httpUriRequest.setConfig(requestConfig);
		
		// add head 
		if (headers != null) {
			for (Iterator<Entry<String, String>> iterator = headers.entrySet()
					.iterator(); iterator.hasNext();) {
				Entry<String, String> entry = iterator.next();
				httpUriRequest.addHeader(entry.getKey(), entry.getValue());
			}
		}
		CloseableHttpResponse response =null;
		
		int retryed=-1;
		while(retryed++<retry){
			try{
				response = closeableHttpClient.execute(httpUriRequest);
				break;
			}catch(java.net.SocketTimeoutException e ){
				if(retryed>=retry){
					throw e;
				}
				continue;
			}
		}
		
		try {
			StatusLine statusLine=response.getStatusLine();
			if(statusLine.getStatusCode()==200){
				InputStream inputStream=response.getEntity().getContent();
				try{
					byte[] bytes=JStringUtils.getBytes(inputStream);
					if(responseHandler==null)
						return stringJResponseHandler.process(bytes);
					else
						return responseHandler.process(bytes);
				}finally{
					inputStream.close();
				}
			} 
		}finally {
			response.close();
		}
		return null;
	}
	
	/**
	 * get HTTP request style . GET or POST 
	 * @return
	 */
	protected abstract HttpRequestBase getHttpType() ;
	
	
	protected HttpClientBuilder getHttpClientBuilder() {
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		configureHttpClientBuilder(httpClientBuilder);
		return httpClientBuilder;
	}

	private void configureHttpClientBuilder(HttpClientBuilder httpClientBuilder) {
		if(proxyHost!=null){
			HttpHost proxy = new HttpHost(proxyHost,proxyPort);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			httpClientBuilder.setRoutePlanner(routePlanner);
		}
	}
	
	
}
