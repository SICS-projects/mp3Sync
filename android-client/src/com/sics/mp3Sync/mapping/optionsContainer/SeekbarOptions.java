/**
 * 
 */
package com.sics.mp3Sync.mapping.optionsContainer;

/**
 * @author joachim
 *
 */
public class SeekbarOptions extends OptionsBase {
	
	public int progress = 0;
	public int secondaryProgress = 0;
	
	public SeekbarOptions() {
		super.optionsType = OptionsTypes.SEEKBAR_OPTIONS;
	}
}
