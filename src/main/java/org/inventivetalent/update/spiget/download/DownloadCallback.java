
package org.inventivetalent.update.spiget.download;

public interface DownloadCallback {

    void finished();

    void error(Exception exception);

}
