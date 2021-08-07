// IMyAidlInterface.aidl
package com.a.notification;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


	/**
	 * 为AIDL服务的接口方法，调用AIDL服务的程序需要调用该方法
	 * @return {@link String}
	 */
    String getValue();

    int getAidlInt();
}