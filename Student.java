import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Student extends UnicastRemoteObject implements IStudent {
    protected Student() throws RemoteException {
    }

    @Override
    public String getStudentInfo(int id) throws RemoteException {
        return "";
    }
}
