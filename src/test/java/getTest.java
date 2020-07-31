import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class getTest {

    @Test(description = "GET")
    public void test_01() {

        //Инициализируем базовый URI
        RestAssured.baseURI = "https://reqres.in/api";

        //Создаем спецификацию ответа от URL
        Response response = given().get("/users?page=2");

        //Создаем коллекцию объектов User, что бы поместить туда объекты с замапеными данными
        LinkedList<User> users = new LinkedList<User>();

        //Создаем коллекцию из данных поля "data" JSON ответа
        List responseList = response.jsonPath().getList("data");

        //Маппим данные в объекты типа User и проверяем на notNull
        for (int i = 0; i < responseList.size(); i++) {
            String data = "data[" + i + "]";
            users.add(new User(response.jsonPath().getMap(data)));
            Assert.assertTrue(users.get(i).checkData());
        }
    }

    @Test(description = "POST")
    public void test_02(){

        //Инициализируем базовый URI
        RestAssured.baseURI = "https://reqres.in/api";

        //Создаем JSON объект, что бы присвоить параметры будущего запроса
        JSONObject params = new JSONObject();

        //Создаем спецификацию POST запроса
        RequestSpecification request = given();

        //Создаем коллекцию объектов User, что бы поместить туда объекты с замаппеными данными
        LinkedList<User> users = new LinkedList<User>();

        //Присваиваем параметры запроса JSON объекту
        params.put("id", 13);
        params.put("email", "batman@reqres.in");
        params.put("first_name", "I'm");
        params.put("last_name", "Batman");
        params.put("avatar", "https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg");

        //Передаем параметры в POST запрос
        request.header("Content-Type", "application/json; charset=utf-8");
        request.body(params);

        //Создаем спецификаию ответа POST запроса
        Response response = request.post("/users");

        //Добавляем объекты типа User (Первый с параметрами ответа POST запроса, второй с параметрам POST запроса) в коллекцию.
        users.add(new User(response.jsonPath().getMap("map")));
        users.add(new User(params.toMap()));

        Assert.assertTrue(users.get(0).userEquals(users.get(1)));
    }

    @Test
    public void test_03() throws InterruptedException {

        double usdSell;
        double usdBuy;
        double eurSell;
        double eurBuy;

        //Устанавливаем Google Chrome как браузер, в котором будет проходить тестирование
        Configuration.browser = "chrome";

        //Открываем страницу "https://www.google.com/"
        open("http://google.com/");

        //Вводим значение "Открытие" в поле поиска и нажимаем Поиск
        $(By.name("q")).val("Открытие").pressEnter();

        //В блоке "Результаты поиска" проверяем наличие текста "www.open.ru", далее кликаем по ссылке принадлежащей этому тексту;
        $("#search").shouldHave(text("www.open.ru")).find(".r>a").click();

        //Инициализируем блок "Курс обмена" как элемент, что бы было проще к нему обращаться
        SelenideElement rates = $(By.xpath("//*[@id=\"main\"]/div/div/div[7]/section/div/div/div[1]/div/div"));

        //Парсим курсы с блока "Курс обмена"
        usdBuy = Double.parseDouble(rates
                .findElement(By.xpath("//*[@id=\"main\"]/div/div/div[7]/section/div/div/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/table/tbody/tr[2]/td[2]/div/span"))
                .getText()
                .replace(',', '.'));
        usdSell = Double.parseDouble(rates
                .findElement(By.xpath("//*[@id=\"main\"]/div/div/div[7]/section/div/div/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/table/tbody/tr[2]/td[4]/div/span"))
                .getText()
                .replace(',', '.'));
        eurBuy = Double.parseDouble(rates
                .findElement(By.xpath("//*[@id=\"main\"]/div/div/div[7]/section/div/div/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/table/tbody/tr[3]/td[2]/div/span"))
                .getText()
                .replace(',', '.'));
        eurSell = Double.parseDouble(rates
                .findElement(By.xpath("//*[@id=\"main\"]/div/div/div[7]/section/div/div/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/table/tbody/tr[3]/td[4]/div/span"))
                .getText()
                .replace(',', '.'));

        //Проверяем результат
        Assert.assertTrue(eurSell > eurBuy);
        Assert.assertTrue(usdSell > usdBuy);


    }
}
