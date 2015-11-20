package org.tusiri.ai2;

public class voteCandidate {
	private String label;
	private int vote;

	
	public voteCandidate(){
		int vote = 0;
	}
	
	public voteCandidate(String label, int vote) {
		this.label = label;
		this.vote = vote;
	}
	
	
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}

}
