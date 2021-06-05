package net.stf.openstarcam;

import android.location.Location;

import org.shredzone.commons.suncalc.MoonPosition;

import java.util.Date;

public class MoonObject {

    private MoonPosition.Parameters mPar;
    private MoonPosition mPos;
    private final LocationSupplier.LocationInfo locationInfo = new LocationSupplier.LocationInfo();

    public MoonObject() {
        mPar = MoonPosition.compute().now();
    }

    public void Update() {
        mPar = MoonPosition.compute().now();
        mPos = mPar.execute();
    }

    public void setPos(Location loc) {
        if (loc != null) {
            setPos(loc.getLatitude(), loc.getLongitude());
        }
    }

    public void setPos(MyApplicationInterface appIFace) {
        Location location = appIFace.getLocation();
        if (location != null) {
            setPos(location.getLatitude(), location.getLongitude());
        }
    }

    public void setPos(double Lat, double Lng) {
        mPar = MoonPosition.compute().at(Lat, Lng).now();
        mPos = mPar.execute();
    }

    public void setPos(double Lat, double Lng, Date date) {
        mPar = MoonPosition.compute().at(Lat, Lng).on(date);
        mPos = mPar.execute();
    }

    public double Azimuth() {
        return mPos.getAzimuth();
    }

    public double Altitude() {
        return mPos.getAltitude();
    }

    public double Distance() {
        return mPos.getDistance();
    }

    public double ParallacticAngle() {
        return mPos.getParallacticAngle();
    }

    public double AngleDiff(double cam_Angle, double cam_Pitch) {
        double dAz = mPos.getAzimuth() - cam_Angle;
        double dPitch = cam_Pitch - mPos.getAltitude();
        double dAngle;
        try {
            dAngle = Math.atan2(dAz, dPitch);
            dAngle = dAngle + Math.PI*0.5;
            // Because the cam_Pitch 0 is to the right
        } catch (Exception e) {
            dAngle = 400.0; // to see the problem
        }
        return dAngle;
    }

    public double GeoDistance(double cam_Angle, double cam_Pitch) {
        double dAz = mPos.getAzimuth();
        double dPitch = mPos.getAltitude();
        double dX = cam_Angle - dAz;
        double dY = cam_Pitch - dPitch;
        return Math.sqrt(dX * dX + dY * dY);
    }
}
