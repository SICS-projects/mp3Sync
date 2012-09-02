/**
 * 
 */
package com.sics.mp3Sync.eventListener;

import java.util.EventListener;



/**
 * @author joachim
 *
 */
public interface DownloadListenerInterface extends EventListener {
	void onDownloadFinished(Object... o);
	void onDownloadProgress(Integer progress);
}
