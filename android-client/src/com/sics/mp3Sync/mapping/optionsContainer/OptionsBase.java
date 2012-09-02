/**
 * 
 */
package com.sics.mp3Sync.mapping.optionsContainer;

/**
 * @author joachim
 *
 */
public class OptionsBase {
	public enum OptionsTypes {NONE, SEEKBAR_OPTIONS}
	
	public OptionsTypes optionsType = OptionsTypes.NONE;
	public int visibility = -1;
}
