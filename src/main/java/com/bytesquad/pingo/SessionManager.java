package com.bytesquad.pingo;

import com.bytesquad.pingo.model.User;

public class SessionManager {
    private static SessionManager instance;
    private User localUser;
    private String serverIp;
    private int serverPort;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getLocalUser() { return localUser; }
    public void setLocalUser(User localUser) { this.localUser = localUser; }

    public String getServerIp() { return serverIp; }
    public void setServerIp(String serverIp) { this.serverIp = serverIp; }

    public int getServerPort() { return serverPort; }
    public void setServerPort(int serverPort) { this.serverPort = serverPort; }
}