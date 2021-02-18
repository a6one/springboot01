package com.uplooking.thread.threadDesign;

import java.util.HashMap;
import java.util.Map;

public class ImmutableObject {


    static final class MMSCRouter {

        // volatile 保证地址引用在其他线程之间是可见的
        private static volatile MMSCRouter instance = new MMSCRouter();

        private final Map<String, MMSCInfo> routeMap;

        public MMSCRouter() {
            routeMap = new HashMap<>();
        }

        public static void setInstance(MMSCRouter instance) {
            MMSCRouter.instance = instance;
        }
    }

    static final class MMSCInfo {
        private final String deviceID;
        private final String url;
        private final int maxAttachmentSizeInBytes;

        public MMSCInfo(String deviceID, String url, int maxAttachmentSizeInBytes) {
            this.deviceID = deviceID;
            this.url = url;
            this.maxAttachmentSizeInBytes = maxAttachmentSizeInBytes;
        }
    }

    class OMCAgent extends Thread {

        @Override
        public void run() {
            boolean isTableModificationMsg = false;
            String updateTableName = null;
            while (true) {
                if (isTableModificationMsg) {
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
        }
    }
}
