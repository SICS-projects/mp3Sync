/**
 * 
 */
package JGApps.MP3Synch.EventListener;

import java.util.EventListener;

import JGApps.MP3Synch.Events.FinishedDownloadEvent;

/**
 * @author joachim
 *
 */
public interface DownloadListenerInterface extends EventListener {
	void onDownloadFinished(FinishedDownloadEvent e);
}
