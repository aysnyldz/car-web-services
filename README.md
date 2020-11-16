# car-web-services
## PROJE GEREKSİNİMLERİ
### Araba Arama Servisi

Aşağıdaki araç bilgileri bir txt dosyadan okuyacak ve bunların içinde arama yapacak bir servis oluşturulacak. Servis input da verilecek olan 2 parametreye göre çalışmalı;

searchCriteria(marka,model,sınıf,hepsi) ve searchKey((T yazarsam bütün t gecenler gelmeli)) ile arama yapıp otomobil modeli(marka, model, sınıf) listesi dönecek rest ve soap ile çalışacak webservisi (spring boot ile maven) kullanılacak.

## PROJE OLUŞTURMA AŞAMALARI

SpringBoot, maven projesi oluşturdum.

Öncelikle Otomobil entity modelini oluşturdum.

Search işlemi için Spring Data JPA kullandım. OtomobilRepository sınıfını oluştururken JpaSpecificationExecutor<Otomobil> kullandım. JpaRepository sayesinde otomatik olarak CRUD işlemlerini buradan kullanabiliyoruz.
  
Arama yaptığımız operasyonları tutacağımız SearchOperation enumını oluşturdum. Burada searchCriteria için EQUAL searchKey için MATCH operatörlerini kullandım. Ayrıca bu metodlara “hepsi” argümanı verildiğinde tüm alanlarda arama yapabilmek için ALL operatörünü ekledim.

Query parametrelerini tutmak için SearchCriteria sınıfını oluşturdum, key ile field adını, operation ile işlem bilgisini ve value ile fieldın değerini tutuyoruz. Bu sınıf aynı zamandarequest edilen bilginin parse edildikten sonra tutulduğu modeli ifade ediyor.

Spring Data JPA Specificationları, kendi custom querylerimizi oluşturmamızı sağlıyor bundan yararlanarak GenericSpesification ve SearchSpecification sınıflarını oluşturdum. Sorguyu oluşturmak için kendi kısıtlamamızı geçmiş oluyoruz.

OtomobilService ve OtomobilServiceImpl servis sınıflarını oluşturdum. OtomobilServiceImpl içinde searchCriteria ve searchKey metodlarını oluşturdum, filtreli arama işlemini gerçekleştirmek  için repositoryden findAll()  metodunu kullandım. Dinamik arama işlemlerini gerçekleştirmek için searchCriteria metodu için GenericSpesification, searchKey metodu için SearchSpecification sınıflarını oluşturmuştum.

Controller kısmı için ise OtomobilController sınıfını oluşturdum. SearchCriteria işlemi için URL’e girilen inputu search değişkeninde, searchKey işlemi için inputu key değişkeninde tuttum. 

Url den okurken Base64 encoderdan geçirip sonrasıda decode ederek kullandım. Bu işlemi url’e girilen türkçe karakterleri ve escape karakterlerini handle edebilmek için gerçekleştirdim. 

Sonrasında createSearchCriteria request edilen URL pattern ve match işlemlerinden geçirerek gruplara ayırmış oldum. Her bir grup farklı bir arama işlemine denk geleceği için her biri için SearchCriteria oluşturdum. Servise elde ettiğim SearchCriteria listesini parametre olarak geçip, yapmak istediğim operator (EQUAL, MATCH, ALL) bilgisini de gönderdim.

### Test işlemleri

searchCriteria ve searchKey işlemleri için kullandığımız OtomobilService servisinin gönderdiğim searchcriterialara göre doğru sonuçlar dönüp dönmediğini test ettim. Bunun için aşağıdaki test caselerini oluşturdum ve başarılı bir şekilde testi geçtiğini gözlemledim.

givenSearchCriteria_whenGettingListOfOtomobil_thenCorrect

givensearchKey_whenGettingListOfOtomobil_thenCorrect

givenSearchCriteriaHepsi_whenGettingListOfOtomobil_thenCorrect

givensearchKeyHepsi_whenGettingListOfOtomobil_thenCorrect

<h2>REQUEST AND RESPONSE</h2>

INPUT : http://localhost:8080/otomobil/searchCriteria?search=sinif:Otomobil
[{"id":1,"marka":"Audi","model":"A3","sinif":"Otomobil"},{"id":2,"marka":"Audi","model":"A4","sinif":"Otomobil"},{"id":3,"marka":"Audi","model":"A5","sinif":"Otomobil"},{"id":4,"marka":"Audi","model":"A6","sinif":"Otomobil"},{"id":5,"marka":"Audi","model":"A7","sinif":"Otomobil"}]

INPUT : http://localhost:8080/otomobil/searchCriteria?search=marka:Audi;model:A7;sinif:Otomobil

[{"id":5,"marka":"Audi","model":"A7","sinif":"Otomobil"}]

INPUT : http://localhost:8080/otomobil/searchCriteria?search=hepsi:BMC
[{"id":6,"marka":"BMC","model":"Belde","sinif":"Otobüs"},{"id":7,"marka":"BMC","model":"ProBus","sinif":"Otobüs"},{"id":8,"marka":"BMC","model":"ProCity","sinif":"Otobüs"},{"id":9,"marka":"BMC","model":"Fatih","sinif":"Kamyon&Kamyonet"},{"id":10,"marka":"BMC","model":"Levend","sinif":"Kamyon&Kamyonet"}]

INPUT : http://localhost:8080/otomobil/searchKey?key=marka:u
[{"id":1,"marka":"Audi","model":"A3","sinif":"Otomobil"},{"id":2,"marka":"Audi","model":"A4","sinif":"Otomobil"},{"id":3,"marka":"Audi","model":"A5","sinif":"Otomobil"},{"id":4,"marka":"Audi","model":"A6","sinif":"Otomobil"},{"id":5,"marka":"Audi","model":"A7","sinif":"Otomobil"},{"id":11,"marka":"Audi","model":"Q5","sinif":"Arazi,SUV&Pick-up"},{"id":12,"marka":"Audi","model":"Q7","sinif":"Arazi,SUV&Pick-up"}]

INPUT : http://localhost:8080/otomobil/searchKey?key=marka:a;model:a

[{"id":1,"marka":"Audi","model":"A3","sinif":"Otomobil"},{"id":2,"marka":"Audi","model":"A4","sinif":"Otomobil"},{"id":3,"marka":"Audi","model":"A5","sinif":"Otomobil"},{"id":4,"marka":"Audi","model":"A6","sinif":"Otomobil"},{"id":5,"marka":"Audi","model":"A7","sinif":"Otomobil"}]

INPUT : http://localhost:8080/otomobil/searchKey?key=hepsi:t
[{"id":1,"marka":"Audi","model":"A3","sinif":"Otomobil"},{"id":2,"marka":"Audi","model":"A4","sinif":"Otomobil"},{"id":3,"marka":"Audi","model":"A5","sinif":"Otomobil"},{"id":4,"marka":"Audi","model":"A6","sinif":"Otomobil"},{"id":5,"marka":"Audi","model":"A7","sinif":"Otomobil"},{"id":6,"marka":"BMC","model":"Belde","sinif":"Otobüs"},{"id":7,"marka":"BMC","model":"ProBus","sinif":"Otobüs"},{"id":8,"marka":"BMC","model":"ProCity","sinif":"Otobüs"},{"id":9,"marka":"BMC","model":"Fatih","sinif":"Kamyon&Kamyonet"},{"id":10,"marka":"BMC","model":"Levend","sinif":"Kamyon&Kamyonet"}]


<h2>This Spring Boot application demonstrates;</h2>
<ul>
<li>Providing Rest Services with Spring Boot</li>
<li>H2 in-memory database</li>
<li>Spring Data JPA implementation</li>
<li>Repository and Service mocking with Mockito and Mockito Annotations</li>
</ul>






