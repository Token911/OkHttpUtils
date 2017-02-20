package com.gz.okhttp.request;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 封装请求参数
 */

public class RequestParams {
    public ConcurrentHashMap<String,String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String,Object> fileParams = new ConcurrentHashMap<>();

    /**
     * Constructs a new empty {@code RequestParams} instance.
     */
    private RequestParams(){
        this((Map<String,String>) null);
    }
    /**
     * Constructs a new RequestParams instance containing the key/value string
     * params from the specified map.
     *
     * @param source
     *            the source key/value string map to add.
     */
    private RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Constructs a new RequestParams instance and populate it with a single
     * initial key/value string param.
     *
     * @param key
     *            the key name for the intial param.
     * @param value
     *            the value string for the initial param.
     */
    private RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {
            {
                put(key, value);
            }
        });
    }

    /**Create New RequestParams*/
    public static RequestParams builder(){
        return new RequestParams();
    }
    /**Create New RequestParams*/
    public static RequestParams builder(final String key, final String value){
        return new RequestParams(key,value);
    }
    /**Create New RequestParams*/
    public static RequestParams builder(Map<String, String> source){
        return new RequestParams(source);
    }

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key
     *            the key name for the new param.
     * @param value
     *            the value string for the new param.
     */
    public RequestParams put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
        return this;
    }

    public RequestParams put(String key, Object object) throws FileNotFoundException {

        if (key != null) {
            fileParams.put(key, object);
        }
        return this;
    }
}
