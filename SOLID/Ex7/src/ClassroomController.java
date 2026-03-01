public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        Projector pj = reg.getFirstOfType("Projector");
        pj.powerOn();
        pj.connectInput("HDMI-1");

        BrightnessControllable lights = reg.getFirstOfType("LightsPanel");
        lights.setBrightness(60);

        TemperatureControllable ac = reg.getFirstOfType("AirConditioner");
        ac.setTemperatureC(24);

        AttendanceScannable scan = reg.getFirstOfType("AttendanceScanner");
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        Powerable pj = reg.getFirstOfType("Projector");
        pj.powerOff();
        
        Powerable lights = reg.getFirstOfType("LightsPanel");
        lights.powerOff();
        
        Powerable ac = reg.getFirstOfType("AirConditioner");
        ac.powerOff();
    }
}
