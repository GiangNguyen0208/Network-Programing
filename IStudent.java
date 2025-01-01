import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStudent extends Remote {
    String getStudentInfo(int id) throws RemoteException;

}
