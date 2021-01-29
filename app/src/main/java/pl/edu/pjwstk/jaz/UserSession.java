package pl.edu.pjwstk.jaz;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
// sesja by moc wielokorotnir przetrwarzac requesty i by obiekt nie ginal od razu po jednym zapytaniu
// filtr tworzy sie tylko raz i proxy pozawala wielokrotnie wstrzykiwac informacje do jednego filtra (nie potrzebny przy wielokorotnym tworzeniu filtra)
@Component
public class UserSession {
    private boolean isLogged = false;

    public void logIn(){
        isLogged = true;
    }
    public void logOut(){
        isLogged = false;
    }
    public boolean isLoggedIn() {
        return isLogged;
    }
}
