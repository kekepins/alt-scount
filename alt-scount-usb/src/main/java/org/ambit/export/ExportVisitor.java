package org.ambit.export;

import org.ambit.data.sample.CadenceSrc;
import org.ambit.data.sample.DistanceSrc;
import org.ambit.data.sample.FinalActivity;
import org.ambit.data.sample.FirmwareInfo;
import org.ambit.data.sample.GpsBase;
import org.ambit.data.sample.GpsSmall;
import org.ambit.data.sample.GpsTiny;
import org.ambit.data.sample.LatLong;
import org.ambit.data.sample.NotSupported;
import org.ambit.data.sample.Pause;
import org.ambit.data.sample.Periodic;
import org.ambit.data.sample.PeriodicDefinition;
import org.ambit.data.sample.Time;
import org.ambit.data.sample.TimeEvent;

public interface ExportVisitor {
	public void visit(CadenceSrc cadenceSrc);
	public void visit(DistanceSrc distanceSrc);
	public void visit(FinalActivity finalActivity);
	public void visit(FirmwareInfo firmwareInfo);
	public void visit(GpsBase gpsBase);
	public void visit(GpsTiny gpsTiny);
	public void visit(GpsSmall gpsSmall);
	public void visit(LatLong latLong);
	public void visit(NotSupported latLong);
	public void visit(Pause pause);
	public void visit(Time time);
	public void visit(TimeEvent timeEvent);
	public void visit(Periodic periodic);
	public void visit(PeriodicDefinition periodicDefinition);
}
