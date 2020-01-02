package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ImgController {
    @FXML
    ImageView imgView;
    @FXML
    Label ISBN;
    @FXML
    Label ISBN1;
    @FXML
    Label Author;
    @FXML
    Label Edition;
    @FXML
    Label Binding;
    @FXML
    Label Publisher;
    @FXML
    Label Published;

    public void img(String globalISBN) throws IOException {

        String url = "https://isbnsearch.org/isbn/" + globalISBN;
        Document doc = Jsoup.connect(url).header("Content-type", "text/html").get();
        Element content = doc.getElementById("book");
        Elements ps = content.select("p");
        String img = content.getElementsByTag("img").attr("src");
        imgView.setImage(new Image(img));
        ArrayList<String> additionalInformation = new ArrayList<String>();
        additionalInformation.add(content.select("h1").text());
        for (Element p : ps) {
            String tmp = p.text();
            additionalInformation.add(tmp);
        }
        ISBN.setText(additionalInformation.get(1));

        ISBN1.setText(additionalInformation.get(2));

        Author.setText(additionalInformation.get(3));

        Edition.setText(additionalInformation.get(4));

        Binding.setText(additionalInformation.get(5));

        Publisher.setText(additionalInformation.get(6));

        Published.setText(additionalInformation.get(7));

    }
}

