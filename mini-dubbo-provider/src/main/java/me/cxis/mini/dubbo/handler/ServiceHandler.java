package me.cxis.mini.dubbo.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cheng.xi on 2017-04-14 10:51.
 */
public class ServiceHandler implements Runnable {
    private Socket client = null;
    private Map<String,Class<?>> exportedServices;
    public ServiceHandler(Socket client, ConcurrentHashMap<String,Class<?>> exportedServices) {
        this.client = client;
        this.exportedServices = exportedServices;
    }

    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try{
            inputStream = new ObjectInputStream(client.getInputStream());
            String serviceName = inputStream.readUTF();
            String methodName = inputStream.readUTF();
            Class<?>[] parameterTypes = (Class<?>[])inputStream.readObject();
            Object[] arguments = (Object[]) inputStream.readObject();

            Class serviceClass = exportedServices.get(serviceName);

            Method method = serviceClass.getMethod(methodName,parameterTypes);
            Object result = method.invoke(serviceClass.newInstance(),arguments);

            outputStream = new ObjectOutputStream(client.getOutputStream());
            outputStream.writeObject(result);

        }catch (Exception e){

        }finally {
            try {
                if(null != outputStream){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
