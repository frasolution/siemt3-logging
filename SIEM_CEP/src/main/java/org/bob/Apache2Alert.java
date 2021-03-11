/**
 * 
 */
package org.bob;

/**
 * @author Nicolas Roth
 *
 */
public class Apache2Alert {
	private long failedAttempts;

	public Apache2Alert(long failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
	
	public long getFailedAttempts() {
		return failedAttempts;
	}	
}
