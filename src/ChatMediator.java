import javax.swing.*;
import java.awt.*;

interface Mediator {

    //mediator pattern
    void registerServer(ServerParticipant serverParticipant);

    void registerClient(ClientParticipant clientParticipant);

    void sendMessage(String message, Participant sender);
}
class ChatMediator implements Mediator {
    private ServerParticipant serverParticipant;
    private ClientParticipant clientParticipant;

    @Override
    public void registerServer(ServerParticipant serverParticipant) {
        this.serverParticipant = serverParticipant;
    }

    @Override
    public void registerClient(ClientParticipant clientParticipant) {
        this.clientParticipant = clientParticipant;
    }

    @Override
    public void sendMessage(String message, Participant sender) {
        if (sender instanceof ServerParticipant) {
            clientParticipant.showMessage(message);
        } else if (sender instanceof ClientParticipant) {
            serverParticipant.showMessage(message);
        }
    }
}
abstract class Participant {
    protected Mediator mediator;
    protected String userID;

    public Participant(String userID, Mediator mediator) {
        this.userID = userID;
        this.mediator = mediator;
    }

    public abstract void sendMessage(String message);

    public abstract void showMessage(String message);
}

class ServerParticipant extends Participant {
    public ServerParticipant(String userID, Mediator mediator) {
        super(userID, mediator);
    }

    @Override
    public void sendMessage(String message) {
        mediator.sendMessage(message, this);
    }

    @Override
    public void showMessage(String message) {
        JPanel panel = Server.formatLabel(message);

        JPanel left = new JPanel(new BorderLayout());
        left.add(panel, BorderLayout.LINE_START);
        Server.vertical.add(left);
        Server.f.validate();
    }
}

class ClientParticipant extends Participant {
    public ClientParticipant(String userID, Mediator mediator) {
        super(userID, mediator);
    }

    @Override
    public void sendMessage(String message) {
        mediator.sendMessage(message, this);
    }

    @Override
    public void showMessage(String message) {
        JPanel panel = Client.formatLabel(message);

        JPanel left = new JPanel(new BorderLayout());
        left.add(panel, BorderLayout.LINE_START);
        Client.vertical.add(left);
        Client.f.validate();
    }
}