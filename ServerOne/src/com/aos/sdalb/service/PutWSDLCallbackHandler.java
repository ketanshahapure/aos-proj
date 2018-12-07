
/**
 * PutWSDLCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */

    package com.aos.sdalb.service;

    /**
     *  PutWSDLCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class PutWSDLCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public PutWSDLCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public PutWSDLCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
               // No methods generated for meps other than in-out
                
               // No methods generated for meps other than in-out
                
               // No methods generated for meps other than in-out
                
           /**
            * auto generated Axis2 call back method for retrieveWSDL method
            * override this method for handling normal response from retrieveWSDL operation
            */
           public void receiveResultretrieveWSDL(
                    com.aos.sdalb.service.PutWSDLStub.RetrieveWSDLResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from retrieveWSDL operation
           */
            public void receiveErrorretrieveWSDL(java.lang.Exception e) {
            }
                


    }
    