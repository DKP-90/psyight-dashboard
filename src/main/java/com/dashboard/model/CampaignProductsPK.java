package com.dashboard.model;

import java.io.Serializable;

public class CampaignProductsPK implements Serializable {
    protected Integer cid;
    protected Integer pid;

    public CampaignProductsPK() {}

    public CampaignProductsPK(Integer cid, Integer pid) {
        this.cid = cid;
        this.pid = pid;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampaignProductsPK other = (CampaignProductsPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}
    
}