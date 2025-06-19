package mcv.DAOInterface;

import mvc.Model.Admin;

public interface IAdmin {
    public Admin login(String email, String password);
}