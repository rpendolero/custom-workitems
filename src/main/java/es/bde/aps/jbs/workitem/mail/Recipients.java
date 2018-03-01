/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.bde.aps.jbs.workitem.mail;

import java.util.ArrayList;
import java.util.List;

public class Recipients {

	private List<Recipient> list = new ArrayList<Recipient>();

	/**
	 * @param recipients
	 */
	public void setRecipients(List<Recipient> recipients) {
		for (Recipient recipient : recipients) {
			addRecipient(recipient);
		}
	}

	/**
	 * @param recipient
	 * @return
	 */
	public boolean addRecipient(Recipient recipient) {
		if (!this.list.contains(recipient)) {
			this.list.add(recipient);
			return true;
		}
		return false;
	}

	/**
	 * @param recipient
	 * @return
	 */
	public boolean removeRecipient(Recipient recipient) {
		return this.list.remove(recipient);
	}

	/**
	 * @return
	 */
	public List<Recipient> getRecipients() {
		return this.list;
	}

	/**
	 * @return
	 */
	public Recipient[] toArray() {
		return (Recipient[]) list.toArray(new Recipient[list.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Recipients other = (Recipients) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}

}
