package pl.edu.pjwstk.jaz.average;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AverageController {

    @GetMapping("average")
    public String getAverage(@RequestParam(value = "numbers", required = false) String numbers){

        if(numbers.equals(""))
        {
            return "Put parameters.";
        }

        String[] params = numbers.split(",");
        int counter  = 0;
        double av = 0;

        for(String number : params){
            int param = Integer.parseInt(number);
            counter = counter +1;
            av = av + param;
        }

        double wynik = av/counter;

        Double d = wynik;
        String[] splitter = d.toString().split("\\.");

        BigDecimal rounded = new BigDecimal(wynik);

        if(splitter[1].length() >= 2) {
            rounded = rounded.setScale(2, BigDecimal.ROUND_HALF_UP);
            wynik = rounded.doubleValue();
        }

        if(wynik == (int)wynik)
            {
                return "Average : " + (int)wynik;
            }
            else {
                return "Average : " + rounded;
            }
        }
    }
