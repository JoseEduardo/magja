package net.magja.service.version;

import net.magja.model.version.Version;
import net.magja.service.GeneralService;
import net.magja.service.ServiceException;

public interface VersionRemoteService extends GeneralService<Version> {

  /**
   * get version of modulo
   *
   * @throws ServiceException
   */
  public abstract Version get() throws ServiceException;

}
