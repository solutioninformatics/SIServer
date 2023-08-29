package com.dev.android.serverchecksdk;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class ServerStatus implements Serializable {
  private String status;

  public String getServerStatus() {
    return this.status;
  }

  public void setServerStatus(String serverStatus) {
    this.status = serverStatus;
  }
}
