package net.magja.soap;

public class Configuration {

  private Long id;

  private String apiUser;
  private String apiKey;
  private String remoteHost;

  private boolean httpAuthEnabled = false;
  private String httpUsername;
  private String httpPassword;

  private int defaultAttributeSetId = 4;
  private int defaultRootCategoryId = 2;

  private boolean httpProxyEnabled = false;
  private String httpProxyHost = "localhost";
  private Short httpProxyPort = 8080;

  private boolean httpProxyAuthEnabled = false;
  private String httpProxyUsername;
  private String httpProxyPassword;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getApiUser() {
    return apiUser;
  }

  public void setApiUser(String apiUser) {
    this.apiUser = apiUser;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getRemoteHost() {
    return remoteHost;
  }

  public void setRemoteHost(String remoteHost) {
    this.remoteHost = remoteHost;
  }

  public boolean isHttpAuthEnabled() {
    return httpAuthEnabled;
  }

  public void setHttpAuthEnabled(boolean httpAuthEnabled) {
    this.httpAuthEnabled = httpAuthEnabled;
  }

  public String getHttpUsername() {
    return httpUsername;
  }

  public void setHttpUsername(String httpUsername) {
    this.httpUsername = httpUsername;
  }

  public String getHttpPassword() {
    return httpPassword;
  }

  public void setHttpPassword(String httpPassword) {
    this.httpPassword = httpPassword;
  }

  public int getDefaultAttributeSetId() {
    return defaultAttributeSetId;
  }

  public void setDefaultAttributeSetId(int defaultAttributeSetId) {
    this.defaultAttributeSetId = defaultAttributeSetId;
  }

  public int getDefaultRootCategoryId() {
    return defaultRootCategoryId;
  }

  public void setDefaultRootCategoryId(int defaultRootCategoryId) {
    this.defaultRootCategoryId = defaultRootCategoryId;
  }

  public boolean isHttpProxyEnabled() {
    return httpProxyEnabled;
  }

  public void setHttpProxyEnabled(boolean httpProxyEnabled) {
    this.httpProxyEnabled = httpProxyEnabled;
  }

  public String getHttpProxyHost() {
    return httpProxyHost;
  }

  public void setHttpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
  }

  public Short getHttpProxyPort() {
    return httpProxyPort;
  }

  public void setHttpProxyPort(Short httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
  }

  public boolean isHttpProxyAuthEnabled() {
    return httpProxyAuthEnabled;
  }

  public void setHttpProxyAuthEnabled(boolean httpProxyAuthEnabled) {
    this.httpProxyAuthEnabled = httpProxyAuthEnabled;
  }

  public String getHttpProxyUsername() {
    return httpProxyUsername;
  }

  public void setHttpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
  }

  public String getHttpProxyPassword() {
    return httpProxyPassword;
  }

  public void setHttpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
  }
}
