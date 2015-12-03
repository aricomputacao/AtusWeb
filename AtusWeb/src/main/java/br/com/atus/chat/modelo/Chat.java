package br.com.atus.chat.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 04343650413
 *
 */
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    private String origem;
    private String destino;

    private List<Menssagem> menssagens = Collections
            .synchronizedList(new ArrayList<Menssagem>());

    private List<Menssagem> getMessages() {
        return menssagens;
    }

    public void marcarMsgComoLida(String destino) {
        for (int i = 0; i < menssagens.size(); i++) {
            if (menssagens.get(i).getDestino().equals(destino)) {
                menssagens.get(i).setEntregue(true);
            }
        }
    }

    public List<Menssagem> getMenssagensNaoLidas() {
        List<Menssagem> list = Collections
                .synchronizedList(new ArrayList<Menssagem>());
        for (int i = 0; i < getMessages().size(); i++) {
            if (!getMessages().get(i).isEntregue()) {
                list.add(getMessages().get(i));
            }
        }
        return list;
    }

    public List<Menssagem> getMenssagensNaoLidas(String origem,String destino) {
        List<Menssagem> list = Collections
                .synchronizedList(new ArrayList<Menssagem>());
        for (int i = 0; i < getMessages().size(); i++) {
            if (!getMessages().get(i).isEntregue()) {
                if ( (getMessages().get(i).getAutor().equals(origem) && getMessages().get(i).getDestino().equals(destino)) || (getMessages().get(i).getDestino().equals(origem) && getMessages().get(i).getAutor().equals(destino))) {
                    list.add(getMessages().get(i));
                }
            }
        }
        return list;
    }

    public void setMessages(List<Menssagem> messages) {
        this.menssagens = messages;
    }

    public void addMessage(String messageText, String author, String destino) {
        Menssagem menssagem = new Menssagem();
        menssagem.setConteudo(messageText);
        menssagem.setAutor(author);
        menssagem.setData(new Date());
        menssagem.setDestino(destino);

        menssagens.add(menssagem);
    }

    public Menssagem getMessage() {
        Menssagem result = null;
        if (menssagens.size() > 0) {
            result = menssagens.get(menssagens.size() - 1);
        }
        return result;

    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.origem);
        hash = 29 * hash + Objects.hashCode(this.destino);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chat other = (Chat) obj;
        if (!Objects.equals(this.origem, other.origem)) {
            return false;
        }
        if (!Objects.equals(this.destino, other.destino)) {
            return false;
        }
        return true;
    }

}
