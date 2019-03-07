package com.rommansabbir.parselivequery;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;

import java.net.URI;
import java.net.URISyntaxException;

public class ParseLiveQuery {
    private static final String ERROR_MESSAGE = "Retrieved Object Empty";
    private Context context;
    private ParseLiveQueryInterface parseLiveQueryInterface;
    private ParseLiveQueryClient parseLiveQueryClient;
    private SubscriptionHandling<ParseObject> subscriptionHandling;

    /**
     * Get parent context through constructor.
     * @param context
     */
    public ParseLiveQuery(Context context) {
        this.context = context;
        /**
         * Instantiate interface
         */
        parseLiveQueryInterface = (ParseLiveQueryInterface) context;
        /**
         * Instantiate ParseLiveQueryClient = null
         */
        parseLiveQueryClient = null;
        /**
         * Instantiate SubscriptionHandling = null
         */
        subscriptionHandling = null;
    }

    /**
     * This method is responsible for live value event listener
     * Pass the APP_NAME of the application, don't forget to active parse liveQuery [Doc link here: https://bit.ly/2SnaVTx]
     * Pass a query that you want to subscribe for live event listener, add some constraint on query if you want
     * @param APP_NAME
     * @param liveQuery
     */
    public void addLiveEventListener(String APP_NAME, ParseQuery<ParseObject> liveQuery){
        try {
            /**
             * Instantiate ParseLiveQueryClient with wss
             * @param APP_NAME
             */
            parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(new URI("wss://"+APP_NAME+".back4app.io/"));
            /**
             * Simply subscribe to the query
             */
            subscriptionHandling = parseLiveQueryClient.subscribe(liveQuery);

            /**
             * Handle the subscription, if any new CREATE event occur
             */
            subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, final ParseObject object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            if(object != null){
                                /**
                                 * Notify interface if any data received from db
                                 */
                                parseLiveQueryInterface.onEventListenerSuccess(object);
                            }
                            else {
                                /**
                                 * Notify interface if any error occur
                                 */
                                parseLiveQueryInterface.onEventListenerFailure(ERROR_MESSAGE);
                            }
                        }
                    });
                }
            });

            /**
             * Handle the subscription, if any new UPDATE event occur
             */
            subscriptionHandling.handleEvent(SubscriptionHandling.Event.UPDATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, final ParseObject object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            if(object != null){
                                /**
                                 * Notify interface if any data received from db
                                 */
                                parseLiveQueryInterface.onEventListenerSuccess(object);
                            }
                            else {
                                /**
                                 * Notify interface if any error occur
                                 */
                                parseLiveQueryInterface.onEventListenerFailure(ERROR_MESSAGE);
                            }
                        }
                    });
                }
            });

            /**
             * Handle the subscription, if any new DELETE event occur
             */
            subscriptionHandling.handleEvent(SubscriptionHandling.Event.DELETE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, final ParseObject object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            /**
                             * Notify interface if any data deleted from db
                             */
                            parseLiveQueryInterface.onEventListenerSuccess(object);
                        }

                    });
                }
            });

        }
        /**
         * Catch if any exception occur.
         */
        catch (URISyntaxException e) {
            e.printStackTrace();
            parseLiveQueryInterface.onEventListenerFailure(e.getMessage());
        }
    }

    /**
     * Destroy the callback after it usages
     */
    public void destroyCallback(){
        parseLiveQueryClient.disconnect();
        subscriptionHandling = null;
        context = null;
    }


    public interface ParseLiveQueryInterface {
        void onEventListenerSuccess(ParseObject retrievedObject);
        void onEventListenerFailure(String errorMessage);
    }
}
