package es.bde.aps.jbs.eaijava.test;

import java.util.Collection;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.command.Command;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.Calendars;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.FactHandle.State;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.ViewChangedEventListener;
import org.kie.api.time.SessionClock;

public class TestKieSession implements KieSession {

	public int fireAllRules() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int fireAllRules(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int fireAllRules(AgendaFilter arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int fireAllRules(AgendaFilter arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void fireUntilHalt() {
		// TODO Auto-generated method stub

	}

	public void fireUntilHalt(AgendaFilter arg0) {
		// TODO Auto-generated method stub

	}

	public <T> T execute(Command<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Calendars getCalendars() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Channel> getChannels() {
		// TODO Auto-generated method stub
		return null;
	}

	public Environment getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getGlobal(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Globals getGlobals() {
		// TODO Auto-generated method stub
		return null;
	}

	public KieBase getKieBase() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends SessionClock> T getSessionClock() {
		// TODO Auto-generated method stub
		return null;
	}

	public KieSessionConfiguration getSessionConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerChannel(String arg0, Channel arg1) {
		// TODO Auto-generated method stub

	}

	public void setGlobal(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void unregisterChannel(String arg0) {
		// TODO Auto-generated method stub

	}

	public Agenda getAgenda() {
		// TODO Auto-generated method stub
		return null;
	}

	public EntryPoint getEntryPoint(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends EntryPoint> getEntryPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public QueryResults getQueryResults(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void halt() {
		// TODO Auto-generated method stub

	}

	public LiveQuery openLiveQuery(String arg0, Object[] arg1, ViewChangedEventListener arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(FactHandle arg0) {
		// TODO Auto-generated method stub

	}

	public void delete(FactHandle arg0, State arg1) {
		// TODO Auto-generated method stub

	}

	public String getEntryPointId() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getFactCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public FactHandle getFactHandle(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends FactHandle> Collection<T> getFactHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends FactHandle> Collection<T> getFactHandles(ObjectFilter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(FactHandle arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends Object> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends Object> getObjects(ObjectFilter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public FactHandle insert(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void retract(FactHandle arg0) {
		// TODO Auto-generated method stub

	}

	public void update(FactHandle arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void update(FactHandle arg0, Object arg1, String... arg2) {
		// TODO Auto-generated method stub

	}

	public void abortProcessInstance(long arg0) {
		// TODO Auto-generated method stub

	}

	public ProcessInstance createProcessInstance(String arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance getProcessInstance(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance getProcessInstance(long arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstance> getProcessInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	public WorkItemManager getWorkItemManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public void signalEvent(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void signalEvent(String arg0, Object arg1, long arg2) {
		// TODO Auto-generated method stub

	}

	public ProcessInstance startProcess(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcess(String arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstance(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public KieRuntimeLogger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEventListener(RuleRuntimeEventListener arg0) {
		// TODO Auto-generated method stub

	}

	public void addEventListener(AgendaEventListener arg0) {
		// TODO Auto-generated method stub

	}

	public Collection<AgendaEventListener> getAgendaEventListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<RuleRuntimeEventListener> getRuleRuntimeEventListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeEventListener(RuleRuntimeEventListener arg0) {
		// TODO Auto-generated method stub

	}

	public void removeEventListener(AgendaEventListener arg0) {
		// TODO Auto-generated method stub

	}

	public void addEventListener(ProcessEventListener arg0) {
		// TODO Auto-generated method stub

	}

	public Collection<ProcessEventListener> getProcessEventListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeEventListener(ProcessEventListener arg0) {
		// TODO Auto-generated method stub

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getIdentifier() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void submit(AtomicAction arg0) {
		// TODO Auto-generated method stub

	}

}
