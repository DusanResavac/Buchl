package at.technikumwien.buchl;

import at.technikumwien.buchl.entity.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
@Log
public class DBInitializer {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    TagRepository genreRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    DiscussionRepository discussionRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void handleApplicationReady() {
        log.info("Initialize database ...");

        languageRepository.saveAll(List.of(
                new Language("Deutsch"),
                new Language("Englisch")
        ));

        authorRepository.saveAll(List.of(
                new Author("Kugane", "Maruyama", "https://static.wikia.nocookie.net/overlordmaruyama/images/9/97/KuganeMaruyama.png/revision/latest/scale-to-width-down/720?cb=20200421021117", "Eine Person mit überzogener schwarzen Weste, die eine Brille und eine schwarze Skelett Maske trägt", null),
                new Author("Ovid", "", null, null, null),
                new Author("William", "Shakespeare", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/First_Folio_%28cropped%29.jpg/800px-First_Folio_%28cropped%29.jpg", null, null),
                new Author("Miguel", "Saavedra", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/09/Cervantes_J%C3%A1uregui.jpg/800px-Cervantes_J%C3%A1uregui.jpg", null, null),
                new Author("Gotthold", "Lessing", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Gotthold_Ephraim_Lessing.PNG/330px-Gotthold_Ephraim_Lessing.PNG", null, null),
                new Author("Friedrich", "Schiller", "https://upload.wikimedia.org/wikipedia/commons/7/74/Friedrich_Schiller_by_Ludovike_Simanowiz.jpg", null, null),
                new Author("Charles", "Dickens", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/86/Charlesdickens.jpg/800px-Charlesdickens.jpg", null, null),
                new Author("Lew", "Tolstoi", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/L.N.Tolstoy_Prokudin-Gorsky.jpg/390px-L.N.Tolstoy_Prokudin-Gorsky.jpg", "Ein sehr bärtiger alter Mann auf einem Stuhl in einem Park oder Wald sitzend.", null),
                new Author("Lewis", "Carroll", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Lewis_Carroll_1863.jpg/300px-Lewis_Carroll_1863.jpg", null, null),
                new Author("Rudyard", "Kipling", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Collier_1891_rudyard-kipling.jpg/255px-Collier_1891_rudyard-kipling.jpg", null, null),
                new Author("Mark", "Twain", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/MarkTwain.LOC.jpg/800px-MarkTwain.LOC.jpg", null, null),
                new Author("Hermann", "Hesse", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Hermann_Hesse_1927_Photo_Gret_Widmann.jpg/255px-Hermann_Hesse_1927_Photo_Gret_Widmann.jpg", null, null),
                new Author("Robert", "Musil", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Musil.jpg/330px-Musil.jpg", null, null),
                new Author("Albert", "Camus", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Albert_Camus%2C_gagnant_de_prix_Nobel%2C_portrait_en_buste%2C_pos%C3%A9_au_bureau%2C_faisant_face_%C3%A0_gauche%2C_cigarette_de_tabagisme.jpg/330px-Albert_Camus%2C_gagnant_de_prix_Nobel%2C_portrait_en_buste%2C_pos%C3%A9_au_bureau%2C_faisant_face_%C3%A0_gauche%2C_cigarette_de_tabagisme.jpg", null, null),
                new Author("Marion Zimmer", "Bradley", null, null, null),
                new Author("Karl", "Marx", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Karl_Marx_001.jpg/800px-Karl_Marx_001.jpg", null, null),
                new Author("Edward", "Gibbon", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Edward_Gibbon_by_Henry_Walton_cleaned.jpg/800px-Edward_Gibbon_by_Henry_Walton_cleaned.jpg", null, null),
                new Author("Arthur", "Schopenhauer", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Arthur_Schopenhauer_by_J_Sch%C3%A4fer%2C_1859b.jpg/800px-Arthur_Schopenhauer_by_J_Sch%C3%A4fer%2C_1859b.jpg", null, null),
                new Author("Jostein", "Gaarder", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Jostein_Gaarder_2019%2C_3Sat_Interview_auf_der_Frankfurter_Buchmesse.jpg/1024px-Jostein_Gaarder_2019%2C_3Sat_Interview_auf_der_Frankfurter_Buchmesse.jpg", null, null)
        ));

        Author kugane = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Kugane", "Maruyama");
        Author ovid = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Ovid", "");
        Author william = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("William", "Shakespeare");
        Author miguel = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Miguel", "Saavedra");
        Author gotthold = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Gotthold", "Lessing");
        Author schiller = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Friedrich", "Schiller");
        Author dickens = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Charles", "Dickens");
        Author tolstoi = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Lew", "Tolstoi");
        Author lewis = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Lewis", "Carroll");
        Author kipling = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Rudyard", "Kipling");
        Author twain = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Mark", "Twain");
        Author hesse = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Hermann", "Hesse");
        Author musil = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Robert", "Musil");
        Author camus = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Albert", "Camus");
        Author bradley = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Marion Zimmer", "Bradley");
        Author marx = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Karl", "Marx");
        Author gibbon = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Edward", "Gibbon");
        Author schopenhauer = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Arthur", "Schopenhauer");
        Author gaarder = authorRepository.findFirstByFirstNameIgnoreCaseAndLastNameIgnoreCase("Jostein", "Gaarder");

        Language englisch = languageRepository.findFirstByNameIgnoreCase("Englisch");
        Language deutsch = languageRepository.findFirstByNameIgnoreCase("Deutsch");

        bookRepository.saveAll(List.of(
                new Book("Overlord, Vol. 9 (Light Novel): The Caster of Destruction", englisch, 256, LocalDate.of(2019, 01, 22), "Every year, a conflict breaks out between the Kingdom and the Empire that usually ends as nothing more than a stalemate. But when the Empire's ruler, the Fresh Blood Emperor Jircniv, visits the Great Tomb of Nazarick, Ainz will enter the fray, turning the border skirmish into an all-out war!", kugane, "overlord 9.jpg", "Ein Herrscherskelett mit einer roten Flagge und eine Armee aus bewaffneten Skeletten auf dem Schlachtfeld.", "738054365-2"),
                new Book("Overlord, Vol. 10 (Light Novel): The Ruler of Conspiracy", englisch, 256, LocalDate.of(2019, 05, 21), "After establishing the Kingdom of Darkness, Ainz is determined to turn it into a utopia-a place where many races and creatures can all enjoy unending prosperity. His first step toward his ultimate goal is the expansion of the Adventurers Guild and training of adventurers themselves, which leads him to pay a visit to the Empire! Elsewhere, the regional leaders have been unsettled by the sudden appearance of the Kingdom of Darkness and are quickly plotting their own ways to deal with the upstart nation... Ainz's first actions as ruler of an immortal kingdom have only just begun!", kugane, "overlord 10.jpg", "Ein Skelett im Magiergewand mit einem Zepter steht in der Arena eines stark besuchten Kolosseums.", "468559864-4"),
                new Book("Metamorphosen", deutsch, 502, LocalDate.of(2019, 12, 1), "Kaum ein Werk der römischen Literatur hat eine solche bis in die Gegenwart reichende Rezeption in fast allen künstlerischen Genres erfahren wie die Metamorphosen Ovids. Seine Erzählungen von Dädalus und Ikarus, von Orpheus, Europa oder Narziss gehören zum europäischen Kulturgut.\n" +
                        "Was Ovid als Mythenerzähler vor anderen auszeichnet, ist die tief- und feinsinnige Psychologie, mit der er die Szenarien seiner Geschichten vor uns ausbreitet. Und es ist seine unbescheidene Konzeption einer Zeiten überspannenden Darstellung von der Erschaffung der Welt bis in die eigene Gegenwart.\n" +
                        "Hermann Heiser legt nun eine vollständig neue Prosa-Gesamtübertragung der Metamorphosen vor. Mit einer flüssigen, zeitgemäßen Erzählsprache, die sich dennoch nicht anbiedert, gelingt ihm die Gratwanderung zwischen Ovids epischem Erzählstil und seinem poetischen Anspruch. Er spricht bewusst ein breites Lesepublikum an, das nicht zwingend fachphilologische Voraussetzungen mitbringen muss, um sich mit Genuss und Gewinn auf den großen Erzähler einzulassen. – Das Lese-Buch ist mit einem hilfreichen Personen- und Ortsglossar versehen.\n" +
                        "\n" +
                        "Hermann Heiser studierte Klassische Philologie in Mainz und Zürich. Neben der Tätigkeit als Gymnasiallehrer war er in verschiedenen Bereichen des Theaters aktiv, u.a. als freier Regisseur. Er ist Verfasser von zwei Erzählbänden.", ovid, "metamorphosen.jpg", "Der Anfang der Metamorphosen in der Handschrift Biblioteca Apostolica Vaticana", "593519360-4"),
                new Book("Romeo und Julia", deutsch, 176, LocalDate.of(2017, 3, 3), "»Romeo und Julia«, das wohl berühmteste Liebespaar der Welt, ist eine Tragödie von William Shakespeare und erzählt von den unglücklichen Umständen einer verbotenen Liebe in der italienischen Stadt Verona. Erstmals erschienen 1597, gehört die Lektüre zu den unvergänglichen Werken der Literaturgeschichte.", william, "romeo und julia.jpg", "Ein Prinz im roten Gewand, der in einen Balkon einsteigt und eine Dame am Hals küsst.", "845938035-1"),
                new Book("Don Quijote", deutsch, 1275, LocalDate.of(2010, 8, 31), "Mit grandiosem Einfallsreichtum erzählt Cervantes von den Abenteuern des verarmten Adligen, der in einer Traumwelt vergangener Ritterzeiten lebt, und seines treuen Waffenträgers Sancho Pansa. Ähnlich wie Goethes Faust für die Deutschen ist Don Quijote für die Spanier zum Sinnbild eines nationalen Genius geworden. Die Sympathie des Erzählers für seine Figuren und sein liebevoll-ironischer Ton machen Don Quijote zum wunderbarsten Antihelden der Weltliteratur.", miguel, "don quijote.jpg", "Ein dünner Mann mit einem Speer in einer Ritterrüstung und ein pummeliger sitzender Mann.", "993600645-3"),
                new Book("Nathan der Weise", deutsch, 160, LocalDate.of(2005, 9, 1), "\n" +
                        "\n" +
                        "Die Szene ist Jerusalem zur Zeit der Kreuzzüge. Spannungsreich treffen die drei Weltreligionen Christentum, Judentum und Islam aufeinander. Inmitten eines dramatischen Geschehens um die Rettung seiner Tochter durch einen christlichen Ordensritter, den seinerseits der Sultan vor dem Tod bewahrt hat, steht Nathan der Weise als ruhender Pol der Vernunft, Aufklärung und Toleranz. In der berühmten Ringparabel klärt er die heikle Frage nach der wahren Religion. Keine der drei Weltreligionen ist absolut. Jede erweist ihre Wahrheit und ihren Sinn erst durch die Kraft, mit der sie praktische Humanität stiften kann. Nathan gelingt es, die Menschen verschiedenen Glaubens in friedvollem Miteinander als Angehörige einer großen Familie zu vereinen.\n" +
                        "\n" +
                        "Gotthold Ephraim Lessing, geb. 1729 in Kamenz/Oberlausitz, kam als Pfarrerssohn und drittes von zwölf Kindern zur Welt. Nach dem Abitur studierte er zunächst Theologie, wandte sich aber bald den philologischen Fächern zu. Der Schriftsteller arbeitete als Dramaturg für das Hamburger Nationaltheater und verfasste zahlreiche berühmte Werke. Privat hatte Lessing 1777/78 sowohl den Tod seines Sohnes als auch den seiner Frau zu verkraften. Er starb am 15. Februar 1781 vereinsamt in Braunschweig. Die Uraufführung seines \"Nathan\" im Jahr 1783 erlebte er nicht mehr. Als bedeutendster Dichter, Denker und Kritiker der Aufklärung, dessen Genialität sogar Goethe bewunderte, gilt er heute als erster moderner Autor Deutschlands.\n", gotthold, "nathan der weise.webp", "Ein älterer Herr in einem lockeren Gewand mit einem überzogenen gelben Tuch auf der Schulter.", "257098272-5"),
                new Book("Don Carlos, Infant von Spanien", deutsch, 260, LocalDate.of(2016, 4, 9), "Don Carlos ist Schillers viertes Drama, das er als dramatisches Gedicht bezeichnete. Nach umfangreichen historischen Studien verfasste er es zwischen 1783 und 1787. In Hamburg wurde das Drama in fünf Akten am 29. August 1787 erstmals aufgeführt. Schiller nimmt in seinem Stück Bezug auf historische Ereignisse im 80-jährigen Krieg (1568–1648), in dem niederländische Provinzen um ihre Unabhängigkeit von der spanischen Krone kämpften. Zwischen König Phillip und seinem Sohn (Don Carlos) verarbeitet Schiller erneut einen Generationenkonflikt am Ende eines überkommenen Gesellschaftssystems – zwischen tyrannischen Despoten und dem aufstrebenden Bürgertum", schiller, "don carlos.jpg", null, "620080113-4"),
                new Book("Oliver Twist (Roman)", deutsch, 416, LocalDate.of(2012, 1, 31), "Als Findelkind im Armenhaus einer englischen Kleinstadt aufgewachsen, flüchtet sich der junge Oliver Twist aus den Fängen seines brutalen Lehrherrn nach London. Doch im Moloch der Großstadt gerät er bald an den skrupellosen Hehler Fagin, der ein seltsames Interesse daran zu haben scheint, Oliver in die Welt des Verbrechens hineinzuziehen ... Wegen seiner präzisen Milieuschilderungen und seines entwaffnenden Sinns für skurrile Komik zählt \"Oliver Twist\" bis heute zu den bedeutendsten und beliebtesten Romanen der englischsprachigen Literatur. (Dickens, Charles 1812 - 1870)", dickens, "oliver twist.jpg", "Ein Junge in einem älteren, düsteren und finsteren England.", "797305090-6"),
                new Book("Krieg und Frieden", deutsch, 2098, LocalDate.of(2009, 10, 29), "\n" +
                        "\n" +
                        "In seinem grandiosen Meisterwerk entfaltet Lew Tolstoj auf über 2000 Seiten ein großes Epos über das Schicksal dreier russischer Adelsfamilien in der Zeit der Napoleonischen Kriege Anfang des 19. Jahrhunderts.\n" +
                        "\n" +
                        "In seinen epischen Dimensionen von Tolstoj selbst in die Nähe der Ilias gestellt, behauptet dieser Roman nicht nur seinen Rang als eines der herausragenden Werke der Weltliteratur, sondern er ist auch und vor allem eines: spannende Lektüre.\n" +
                        "\n" +
                        "Lew Tolstoj wurde am 9. September 1828 in Jasnaja Poljana bei Tula geboren und starb am 20. November 1910 in Astapowo, heute zur Oblast Lipezk an einer Lungenentzündung. Tolstoj entstammte einem russischen Adelsgeschlecht. Als er mit neun Jahren Vollwaise wurde, übernahm die Schwester seines Vaters die Vormundschaft. An der Universität Kasan begann er 1844 das Studium orientalischer Sprachen. Nach einem Wechsel zur juristischen Fakultät brach er das Studium 1847 ab, um zu versuchen, die Lage der 350 geerbten Leibeigenen im Stammgut der Familie in Jasnaja Poljana mit Landreformen zu verbessern. Er erlebte von 1851 an in der zaristischen Armee die Kämpfe im Kaukasus und nach Ausbruch des Krimkriegs 1854 den Stellungskrieg in der belagerten Festung Sewastopol. Die Berichte aus diesem Krieg (1855: Sewastopoler Erzählungen) machten ihn als Schriftsteller früh bekannt. Er bereiste aus pädagogischem Interesse 1857 und 1860/61 westeuropäische Länder und traf dort auf Künstler und Pädagogen. Nach der Rückkehr verstärkte er die reformpädagogischen Bestrebungen und richtete Dorfschulen nach dem Vorbild Rousseaus ein. Seit 1855 lebte er abwechselnd auf dem Gut Jasnaja Poljana, in Moskau, und in Sankt Petersburg. Im Jahre 1862 heiratete er die 18-jährige deutschstämmige Sofja Andrejewna Behrs, mit der er insgesamt 13 Kinder hatte. In den folgenden Jahren seiner Ehe schrieb er die monumentalen Romane Krieg und Frieden sowie Anna Karenina, die Tolstojs literarischen Weltruhm begründeten..\n" +
                        "Hermann Röhl, geboren 1851 in Wittstock und verstorben 1923 in Naumburg, war ein deutscher Übersetzer, der viele klassische russische Werke ins Deutsche übertrug.\n", tolstoi, "krieg und frieden.jpg", "Soldaten, die auf Pferden im Schnee reiten und kleine Fahnen hissen.", "193241680-3"),
                new Book("Alice im Wunderland", deutsch, 160, LocalDate.of(2012, 10, 9), "Eines der beliebtesten Bücher der englischen Literatur dank Carrolls meisterlicher Verbindung von Humor und Originalität, von Traum und Märchen. Im Wunderland, in das Alice durch einen Kaninchenbau gelangt, gelten eigene Gesetze, hier wirkt Logik anders, hier sind der Phantasie keine Grenzen gesetzt. Mit exzentrischen Figuren und unvergesslichen Szenen kommentiert Lewis Carroll Zwänge und Reglements unserer Welt. Sein Roman hat Künstler wie Salvator Dalí und James Joyce bezaubert und inspiriert. Große Literatur für Kinder und die Kinder in uns.", lewis, "alice im wunderland.png", null, "042798064-X"),
                new Book("Das Dschungelbuch", deutsch, 244, LocalDate.of(2018, 4, 5), "Die Entwicklung des >>Wolfsjungen<< Mowgli vom Findelkind bis zum Erwachsenenwird in insgesamt acht Erzählungen beschrieben: Mowgli gelangt als Kleinkindzu einem Wolfsrudel, als der Tiger Shir Khan das Dorf seiner Eltern angreiftund alle flüchten. Die Wölfe nehmen ihn in ihre Gemeinschaft auf und lassenihn durch den Bären Balu und den Panther Baghira erziehen. Auch nach seinerLehrzeit im Dschungel hält Mowgli noch Kontakt mit den Wölfen und kannschließlich mit deren Unterstützung seinen Erzfeind Shir Khan besiegen.Kipling verknüpft drei Erzählebenen: die magische Geschichte, die naturgetreueSchilderung sowie eine archetypische Geschichte, die ursprüngliche Menschheitssituationenrituell abhandelt, wie z. B. Mowglis Aufnahme in das Wolfsrudel, die durcheinen Preis erkauft werden muss, oder die Überwindung des Bösen mittelserprobter Fähigkeiten und Freunde.", kipling, "das dschungelbuch.jpg", "Ein großer gestreifter Tiger, der mit kleinen Tigern spielt und auf sie aufpasst.", "524627877-8"),
                new Book("Die Abenteuer von Tom Sawyer und Huckleberry Finn", deutsch, 256, LocalDate.of(2008, 3, 1), "Schule schwänzen, Höhlen erforschen, als Pirat auf dem Mississippi leben: Mark Twains aufmüpfiger, liebenswerter Held Tom Sawyer ist ein Lausbube, wie er im Buche steht. Nicht zufällig gehört ›Tom Sawyer‹ zu den beliebtesten Kinder- und Jugendromanen der Weltliteratur. Darüber hinaus aber ist der Roman mit seiner Kritik am spießbürgerlichen Leben ein bis heute aktueller Gesellschaftsroman, der sich an alle jung gebliebenen Erwachsenen richtet.", twain, "tom sawyer.webp", "Ein Hut, der auf einem Zaun hängt. Im Hintergrund fährt ein Schiff.", "599099251-3"),
                new Book("Der Steppenwolf", deutsch, 277, LocalDate.of(1974, 4, 30), "Harry Haller ist in das kulturlose und unmenschliche Inferno unserer prunkenden und lärmenden Gegenwart vorgedrungen und steht mit seinem Begriff von Menschenwert… einsam außerhalb der bürgerlichen Gesellschaft. Seine Sehnsucht kennt eine unerreichbare Wirklichkeit: seine Verzweiflung treibt ihn zuweilen in die erreichbare andere zurück. Lust und Enttäuschung ihres Daseins führen in seinem Herzen und Hirn einen Kampf, an dem die Zivilisation Europas mit ihrem ganzen Bestände und Befunde teilnimmt.", hesse, "der steppenwolf.jpg", null, "486198251-0"),
                new Book("Der Mann ohne Eigenschaften", deutsch, 1632, LocalDate.of(1969, 12, 1), "Der Mann ohne Eigenschaften», Musils unvollendetes Opus magnum, ist eines der wirkungsmächtigsten Werke der modernen Literatur, «ein letztes Prunkstück österreichischen Barocks, strotzend von Überfülle, Fleisch und Kostüm, Vorhang und Hintergrund, Sinnlichkeit und Reflexion.", musil, "der mann ohne eigenschaften.jpg", null, "295276783-1"),
                new Book("Der Fremde", deutsch, 153, LocalDate.of(1961, 1, 1), "«Mir wurde klar, dass ich das Gleichgewicht des Tages zerstört hatte, die außergewöhnliche Stille eines Strandes, an dem ich glücklich gewesen war. Da habe ich noch viermal auf einen leblosen Körper geschossen, in den die Kugeln eindrangen, ohne dass man es ihm ansah. Und es war wie vier kurze Schläge, mit denen ich an das Tor des Unglücks hämmerte.»\n" +
                        "\n" +
                        "Die Geschichte eines jungen Franzosen in Algerien, den ein lächerlicher Zufall zum Mörder macht, wurde 1942 im besetzten Frankreich zu einer literarischen Sensation. Der Roman bedeutete den schriftstellerischen Durchbruch für Albert Camus und gilt heute als einer der Haupttexte des Existenzialismus.", camus, "der fremde.jpg", null, "646753892-7"),
                new Book("Die Nebel von Avalon", deutsch, 1120, LocalDate.of(1987, 6, 1), "Morgaine, die Hohepriesterin des Nebelreichs Avalon und Schwester von Artus, erzählt die wahre Geschichte ihres königlichen Bruders und der Ritter der Tafelrunde. Zum ersten Mal schildert eine Frau diese Geschichte, zeigt die Heldengestalten in einem neuen Licht und erinnert daran, dass einst Frauen die Macht in den Händen hielten: Sie lenken im Verborgenen das Geschick ihrer Zeit und setzen den König der Legenden auf den Thron, geben ihm das heilige Schwert Excalibur.", bradley, "die nebel von avalon.jpg", "Eine auf einem weißen Pferd reitende Frau. Sie hält ein nach unten zeigendes Schwert an der Klinge.", "576570668-1"),
                new Book("Das Kapital", deutsch, 892, LocalDate.of(2020, 12, 16), "Das Marx’sche Programm einer »Kritik der politischen Ökonomie« ist nach wie vor von gesellschaftlicher und philosophischer Relevanz. Unter dem Titel »Das Kapital« hat Karl Marx zu Lebzeiten 1867 das erste Buch des ersten Bandes zum »Produktionsprozess des Kapitals« vorgelegt. Schon in der ersten Auflage, die dieser neuen Studienausgabe zugrunde liegt, findet sich ein von Marx verfasster Anhang, in dem er das erste Kapitel verständlicher darstellen wollte (eine weiterentwickelte Version dieses Nachtrags bietet dann das erste Kapitel in der zweiten Auflage von 1872, das hier ebenfalls abgedruckt wird). Hintergrund dieser Modifikationen ist, dass Marx seine Wertlehre mittels philosophischer Kategorien und Modelle organisiert hat, die er aus der Philosophie Hegels übernahm. Der Schwerpunkt des Herausgeber-Kommentars liegt auf der Darstellung der philosophischen Dimension der Marx’schen Theoriebildung. Dazu werden einleitend die Pfade der Marx’schen Hegelrezeption sowie die systematischen Bezüge zwischen den Marx’schen Frühschriften und dem »Kapital« erläutert. Darüber hinaus werden in Form eines Stellenkommentars die zentralen philosophischen Kategorien und Theoreme des Marx’schen Hauptwerkes identifiziert und expliziert. So macht diese Neuausgabe nicht nur die Kontinuität des Marx’schen Denkens sichtbar, sondern auch deutlich, dass Marx’ Kritik der politischen Ökonomie ein der dialektischen Philosophie Hegels zutiefst verpflichtetes philosophisches Programm ist.", marx, null, null, "783661201-5"),
                new Book("Verfall und Untergang des Römischen Reiches", deutsch, 328, LocalDate.of(2000, 5, 1), "Verfall und Untergang des Römischen Reiches' liest sich frisch wie kaum ein anderer Text aus dem späten 18. Jahrhundert. Die neuere Geschichtsforschung mag zwar einen Teil der dargestellten Fakten revidiert haben, aber die hohe literarische Qualität des Werkes, seine feine Ironie und sein geistreicher Humor machen es unsterblich.\n" +
                        "Die Geschichte des Römischen einschließlich des Byzantinischen Reichs von der Mitte des zweiten Jahrhunderts nach Christus bis zur Einnahme Konstantinopels durch die Türken im Jahre 1453 schildert.\n" +
                        "Die Hauptschuld am Untergang des Römischen Reiches gibt Gibbon dem Christentum, der Dekadenz sowie dem Einbruch der Germanen. Im Byzantinischen Reich sah er eine Fortsetzung und Steigerung dieser Dekadenz, weshalb es seiner Ansicht nach eine orientalische Despotie war, die den Namen \"Römisches Reich\" nicht verdiente. Im Gegensatz dazu sah er die seiner Analyse nach naturhaft-gesunden jungen Reiche des mittelalterlichen Nord- und Westeuropa.\n" +
                        "Auch auf die deutsche Geschichtsschreibung hatte Gibbon großen Einfluss. Dieses ist u. a. an der sechsbändigen römischen Geschichte von Wilhelm Drumann zu sehen!", gibbon, "verfall und untergang des roemischen reiches.jpg", null, "838869493-6"),
                new Book("Die Welt als Wille und Vorstellung", deutsch, 992, LocalDate.of(2009, 10, 31), "'Die Welt ist meine Vorstellung' – Arthur Schopenhauers (1788–1860) berühmter Satz steht am Beginn seines philosophischen Hauptwerks 'Die Welt als Wille und Vorstellung': Wer die Welt in Begriffe fasst, bleibt hinter dem Eigentlichen zurück, nur der Blick auf den Leib bringt die essenzielle menschliche Triebkraft zutage, den Willen. 'Reine' Erkenntnis wohnt nach Schopenhauer allein in der Kunst, was dieser in seinen Augen gegenüber Wissenschaft und Philosophie eine Sonderstellung verleiht. Sein sprachgewaltiges Traktat beeinflußte zahlreiche Denker, Schriftsteller und Künstler.", schopenhauer, "die welt als wille und vorstellung.jpg", null, "426133869-6"),
                new Book("Sofies Welt", deutsch, 624, LocalDate.of(1993, 8, 5), "Ein Roman über zwei ungleiche Mädchen und einen geheimnisvollen Briefeschreiber, ein Kriminal- und Abenteuerroman des Denkens, ein geistreiches und witziges Buch, ein großes Lesevergnügen und zu allem eine Geschichte der Philosophie von den Anfängen bis zur Gegenwart. Ausgezeichnet mit dem Jugendliteraturpreis 1994. Bis zum Sommer 1998 wurde Sofies Welt 2 Millionen mal verkauft.", gaarder, "sofies welt.jpg", null, "896145982-1"),
                new Book("Hey test", deutsch, 240, LocalDate.of(2020, 10, 24), "", kugane, null, null, "some isbn")
        ));

        Tag animals = new Tag(null, "Tiere", null);
        Tag finance = new Tag(null, "Geschäft & Finanzen", null);
        Tag health = new Tag(null, "Gesundheit & Wellness", null);
        Tag arts = new Tag(null, "Kunst", null);
        Tag belletristic = new Tag(null, "Belletristik", null);
        Tag science = new Tag(null, "Wissenschaft & Mathematik", null);
        Tag biographies = new Tag(null, "Biografien", null);
        Tag readingAge = new Tag(null, "Lesealter", null);


        var tagList = genreRepository.saveAll(List.of(
                animals,
                finance,
                health,
                arts,
                belletristic,
                science,
                biographies,
                readingAge
        ));

        animals = tagList.get(0);
        finance = tagList.get(1);
        health = tagList.get(2);
        arts = tagList.get(3);
        belletristic = tagList.get(4);
        science = tagList.get(5);
        biographies = tagList.get(6);
        readingAge = tagList.get(7);


        genreRepository.saveAll(List.of(
                new Tag(arts, "Architektur", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Mann ohne Eigenschaften"))
                ),
                new Tag(arts, "Kunstunterricht", null),
                new Tag(arts, "Kunstgeschichte", null),
                new Tag(arts, "Tanz", null),
                new Tag(arts, "Design", null),
                new Tag(arts, "Mode", null),
                new Tag(arts, "Film", null),
                new Tag(arts, "Grafikdesign", null),
                new Tag(arts, "Musik", null),
                new Tag(arts, "Malerei", null),
                new Tag(arts, "Fotografie", null),
                new Tag(animals, "Bären", List.of(bookRepository.findFirstByTitleIgnoreCase("Das Dschungelbuch"))),
                new Tag(animals, "Katzen", List.of(bookRepository.findFirstByTitleIgnoreCase("Das Dschungelbuch"))),
                new Tag(animals, "Hunde", null),
                new Tag(animals, "Reptilien", null),
                new Tag(belletristic, "Fantasy", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 10"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Quijote"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Alice im Wunderland"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Nebel von Avalon")
                )),
                new Tag(belletristic, "Geschichte", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Metamorphosen"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Carlos"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Verfall und Untergang des Römischen Reiches"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Sofies Welt")
                )),
                new Tag(belletristic, "Horror", null),
                new Tag(belletristic, "Humor", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 10"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Oliver Twist"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Alice im Wunderland"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Dschungelbuch"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Abenteuer von Tom Sawyer")
                )),
                new Tag(belletristic, "Literatur", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Mann ohne Eigenschaften"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Kapital")

                )),
                new Tag(belletristic, "Magie", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 10")
                )),
                new Tag(belletristic, "Rätsel- und Detektivgeschichten", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Sofies Welt")
                )),
                new Tag(belletristic, "Theaterstücke", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Romeo und Julia")
                )),
                new Tag(belletristic, "Poesie", null),
                new Tag(belletristic, "Liebesgeschichten", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Romeo und Julia")
                )),
                new Tag(belletristic, "Science Fiction", null),
                new Tag(belletristic, "Thriller", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Oliver Twist"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Krieg und Frieden"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Fremde")
                )),
                new Tag(science, "Biologie", null),
                new Tag(science, "Chemie", null),
                new Tag(science, "Mathematik", null),
                new Tag(science, "Physik", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Welt als Wille und Vorstellung")
                )),
                new Tag(science, "Programmierung", null),
                new Tag(finance, "Management", null),
                new Tag(finance, "Unternehmertum", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Kapital")
                )),
                new Tag(finance, "Betriebswirtschaftslehre", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Kapital")
                )),
                new Tag(finance, "Unternehmenserfolg", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Kapital")
                )),
                new Tag(finance, "Finanzen", null),
                new Tag(health, "Kochen", null),
                new Tag(health, "Kochbücher", null),
                new Tag(health, "Geistige Gesundheit", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Welt als Wille und Vorstellung")
                )),
                new Tag(health, "Übung", null),
                new Tag(biographies, "Autobiographien", null),
                new Tag(biographies, "Geschichte", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Nathan der Weise")
                )),
                new Tag(biographies, "Politik und Regierung", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Carlos")
                )),
                new Tag(biographies, "Kriege", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Krieg und Frieden")
                )),
                new Tag(biographies, "Frauen", null),
                new Tag(biographies, "Könige und Herrscher", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Nathan der Weise"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Carlos")
                )),
                new Tag(biographies, "Komponisten", null),
                new Tag(biographies, "Künstler", null),
                new Tag(readingAge, "ab 6", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Oliver Twist"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Alice im Wunderland")
                )),
                new Tag(readingAge, "ab 10", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Abenteuer von Tom Sawyer"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Sofies Welt")
                )),
                new Tag(readingAge, "ab 14", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 10"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Romeo und Julia"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Nathan der Weise"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Carlos"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Steppenwolf"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Fremde"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Nebel von Avalon"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Welt als Wille und Vorstellung"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Sofies Welt")
                )),
                new Tag(readingAge, "ab 16", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Quijote"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Krieg und Frieden"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Mann ohne Eigenschaften"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Verfall und Untergang")
                )),
                new Tag(readingAge, "ab 18", List.of(
                        bookRepository.findFirstByTitleContainingIgnoreCase("Metamorphosen"),
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Kapital")
                ))
        ));

        List<User> users = userRepository.saveAll(List.of(
                new User("Nelson", "https://images-eu.ssl-images-amazon.com/images/S/amazon-avatars-global/656232c4-6199-4025-9e42-098b18731394._CR0,0,375,375_SX48_.jpg", null, null),
                new User("Pseudo Nym", null, null, null),
                new User("LishFice", null, null, null),
                new User("Username", null, null, null),
                new User("ICPMCP", "https://images-na.ssl-images-amazon.com/images/S/amazon-avatars-global/f762513c-8f6f-47d9-9a5b-394db7f09f11._CR0,0,500,500_SX48_.jpg", null, null),
                new User("Stefano", null, null, null),
                new User("Heinzelmann", null, null, null),
                new User("CDawgVA", "https://static-cdn.jtvnw.net/jtv_user_pictures/49110706-4c6c-4da5-8037-0fbd429405f5-profile_image-70x70.png", null, null)
        ));

        reviewRepository.saveAll(List.of(
                new Review("Fan translation ist besser als das hier...",
                        "Ich kaufe mir die Bücher eigentlich nur um den Autor und Künstler zu unterstützen aber was Yen press hier diesmal abgezogen hat ist wirklich unglaublich. Wie kann man den bitte einfach so wichtige Punkte der Geschichte ändern \"King of Darkness (hust) \" ich frage mich wirklich wer da nochmal korrigiert im Anime S3 die erst lief würde es oft genug erwähnt \"Sorcerer King\" und vergessen wir nicht die Fan Translations die schon lange das aktuelle Band fertig haben last mich lügen aber sind sicher schon 2 Jahre also warum tut man sowas? Davon mal ab gesehen das Yen Press es nicht hin bekommt ein Buch am Erscheinungstag zu liefern sondern erst 2Wochen später, ein Grund warum ich deswegen schon bei anderen LN nur noch digital kaufe das ist wirklich unglaublich...\n" +
                                "\n" +
                                "Fazit: Yen Press einfach zum Kopfschütteln.\n" +
                                "\n" +
                                "Sämtliche Grammatikfehler die ihr findet dürfen gerne behalten werden.",
                        LocalDate.of(2019, 2, 10),
                        20,
                        1,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                        users.get(0)),
                new Review("Brillant!",
                        "Ihre Majestät Ainz Ooal Gown ist wirklich ein außergewöhnlicher Mastermind, wie in diesem herausragenden Roman genau dokumentiert ist.\n" +
                                "Wie konnte jemand dieses Ergebnis sehen, außer unserem allwissenden Overlord?\n" +
                                "\n" +
                                "Kann sogar Leuten empfehlen, die den Anime gesehen haben, da die Geschichte hier detaillierter ist und sich in einigen kleinen Punkten unterscheidet, aber nicht schlecht. Gibt dir wirklich mehr Eindrücke von den Charakteren.\n" +
                                "\n" +
                                "Dies war der erste, den ich gekauft habe und jetzt, nach ungefähr einem Monat, fehlt mir nur noch vol. 1.\n" +
                                "Es macht einfach so süchtig.",
                        LocalDate.of(2021, 10, 29),
                        0,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                        users.get(1)),
                new Review("Übersetzung „Darkness King“ ist richtig",
                        "Weil sich einige Leute immer wieder über die Übersetzung des „Sorcerer Kingdom“ in „Kingdom of Darkness“ und auch über den König beschweren: Jemand hat den ursprünglichen Autor gefragt, ob er von dieser „gescheiterten“ Übersetzung wusste. Der Autor sagte, dass der englische Übersetzer ihn nach der richtigen Übersetzung fragte, und er antwortete, dass „King of Darkness“ die genaueste Übersetzung dafür sei. „Sorcerer King“ oder „Witch King“ sind eher eine Metapher.",
                        LocalDate.of(2019, 7, 23),
                        9,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                        users.get(2)),
                new Review("Fesselnd",
                        "Wieder einmal genial. Es gibt neue intrigen und taktiken und ganz zufällig macht Ainz das richtige. Alle fürchten ihn und seine gefolgschaft, wie und ob er es schafft trotzdem ein königreich aufzubauen werden wir wohl bald sehen.",
                        LocalDate.of(2019, 6, 11),
                        1,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 10"),
                        users.get(0)),
                new Review("Zum Teil falsch übersetzt, sonst aber gut",
                        "An dem Light Novel Vol. 10 lässt sich eigentlich nur eine Sache bemängeln, und zwar wurde wie bei Vol. 9 teils schlecht übersetzt. Es lautet \"sorcerer king\" nicht \"King of Darkness\", und \"sorcerer kingdom\" nicht \"Nation of Darkness\". Das macht das lesen stellenweise wirklich anstrengend. Ansonsten aber ein tolles Buch.",
                        LocalDate.of(2019, 6, 5),
                        6,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 10"),
                        users.get(2)),
                new Review("Grundlagenlektüre",
                        "Sehr ansprechende und lesbare Übersetzung.",
                        LocalDate.of(2019, 8, 21),
                        4,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Metamorphosen"),
                        users.get(3)),
                new Review("Alles Gut!",
                        "",
                        LocalDate.of(2019, 3, 31),
                        1,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Metamorphosen"),
                        users.get(5)),
                new Review("Sehr schön!",
                        "Eine wunderschöne Ausgabe, die sich ganz wunderbar in meinem Bücherregal macht. Über den Inhalt muss ich glaube ich nicht viel schreiben, Romeo & Julia dürften die meisten kennen. Ein Klassiker, der zumindest in meinem Bücherregal nicht fehlen darf!",
                        LocalDate.of(2022, 1, 8),
                        0,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Romeo und Julia"),
                        users.get(4)),
                new Review("Tolles Buch",
                        "Ein wunderschönes, sehr dekoratives Hardcover im klassischen Design. Fühlt sich extrem hochwertig und angenehm an, beinahe nostalgisch. Ich habe noch andere Bücher aus der Reihe bestellt. Zum weltberühmten Inhalt muss an dieser Stelle wohl nicht viel gesagt werden - Lesen lohnt sich natürlich!",
                        LocalDate.of(2021, 3, 3),
                        3,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Romeo und Julia"),
                        users.get(1)),
                new Review("Ein Schelmenroman der Weltliteratur",
                        "Ein Schelmenroman, manchmal derb, meist amüsant, hie und da etwas langatmige Abweichungen. Der zweite Teil, der 10 Jahre später erschienen ist, hat mir weniger gut gefallen. Es dauert, bis Don Quijote sich wieder auf den Weg macht mit Sancho Pansa. Die Leute wissen nun, mit wem sie es zu tun haben, weil sie den ersten Teil schon gelesen haben. Es sind auch Nachahmungen erschienen, das kommt im 2. Teil zur Sprache. Man vernimmt auch einiges über die damalige Zeit: z.B. die Beschreibung der Galeeren oder die Vertreibung der Morisken in Spanien.\n" +
                                "Interessant ist die Spannung zwischen Sein und Schein. Don Quijote und Sancho Pansa äussern manchmal Irrsinn, manchmal weise Einsichten, vor allem gegen dem Ende zu, wo Don Quijote kurz vor dem Tod realisiert, dass er durch die Lektüre der Ritterromane in einer Scheinwelt gelebt hat. Man ist doch froh, wenn der Roman endlich zu einem Ende kommt. Aber aufgepasst: Don Quijote und Sancho Pansa leben weiter – im Moment sind sie in Washington…",
                        LocalDate.of(2019, 4, 6),
                        4,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Quijote"),
                        users.get(7)),
                new Review("Naja",
                        "Das Buch habe ich als Deutsch-Lektüre erworben und ich sag mal so: Ich fand es war nicht das gelbe vom Ei\n" +
                                "Eher so wie wenn man das Ei genießt und dann auf ein Stück Schale beißt\n" +
                                "Man hat ein wenig gelesen und dann hatte man auch schon keine Lust mehr.\n" +
                                "Wer gerne sehr komplexe Dramen liest, das ist genau dein Buch! Für die anderen eher Finger weg. :)",
                        LocalDate.of(2020, 12, 20),
                        3,
                        3,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Nathan der Weise"),
                        users.get(5)),
                new Review("Für begeisterte Leser- die gerne Mal etwas mehr Zeit aufbringen",
                        "Habe das Buch im Rahmen unseres Deutsch Unterrichts bestellt. Anfangs hatte ich teils Probleme den Text zu verstehen,wobei mir die Worterklärungen am Ende jeder Seite sehr geholfen haben. Das Buch empfiehlt sich für lesebegeisterte die gerne Mal etwas mehr Zeit zum Lesen und verstehen eines Buches aufbringen. Wenn man sich erstmal in das Geschehen,die Geschichtlichen Hintergründe und die Personen eingearbeitet hat vermittelt das Buch die Gedanken der verschiedenen Religionen und wie diese sich vereinbaren lassen in Zeiten den dritten Kreuzzuges.",
                        LocalDate.of(2018, 7, 28),
                        3,
                        3,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Nathan der Weise"),
                        users.get(6)),
                new Review("Schwer verständlich, aber spannend",
                        "Ich musste das Buch für eine Buchpräsentation kaufen.\n" +
                                "Das Buch ist Schiller typisch etwas schwerer zu verstehen, allerdings ist es wirklich spannend, wenn man darüber hinwegschauen kann.\n" +
                                "Das Buch kann man relativ schnell bearbeiten und ist daher eine gute Empfehlung für alle die ebenfalls eine Buchpräsentation halten müssen.\n" +
                                "\n" +
                                "Es sollte noch erwähnt werden, dass das Buch auch als E-Book kostenlos erhältlich ist. Ich wollte allerdings lieber ein Buch das ich anfassen kann und überall mit hinnehmen kann ohne mein Handy oder Laptop dabei zu haben (Besitze kein Kindle oder ähnliches).",
                        LocalDate.of(2015, 12, 5),
                        0,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Don Carlos"),
                        users.get(2)),
                new Review("Sozialkritisches Märchen, spannender Krimi und packendes Drama",
                        "Man mag es kaum glauben, aber Charles Dickens hat viel Autobiographisches in diese Geschichte fließen lassen.\n" +
                                "Er selbst wuchs in ärmlichen Verhältnissen auf und sein Vater saß im Marshalsea-Schuldgefängnis ein, nahm die Familie mit und Charles wurde gezwungen in einer Fabrik zu arbeiten.\n" +
                                "\n" +
                                "Dies ist auch der Grund der äußerst realistischen und präzisen Schilderungen der Verhältnisse in Armenhäusern, der unmenschlichen und kaltherzigen Behandlung von Armen und Schwachen, sowie des kriminellen Milieus. Als unpolitischer Moralist war es Dickens daher ein Anliegen diese Verhältnisse in seinen Romanen offen zu legen. Dies ist seine Art auf das vom Parlament beschlossene Armengesetz, in der materielle Zuwendungen an Bedürftige abgeschafft wurden, zu reagieren.\n" +
                                "\n" +
                                "Auf seine satirische und humoristische Art werden hier die ausführenden Organe, sowie die höhere Gesellschaft an den Pranger gestellt. Dies auf so skurrile und sarkastische Art und Weise, sodass man als Leser des Öfteren ein Schmunzeln auf den Lippen hat. Charles Dickens besaß wirklich einen sehr schwarzen und bösen Humor.\n" +
                                "Doch auch die packende Erzählweise und der Handlungsverlauf machen diesen Roman zu etwas besonderem. Dickens schafft es am Ende eines jeden Kapitels einen Cliffhanger einzubauen und die Handlung selbst entwickelt sich im Verlauf immer mehr zu einem spannenden Thriller. Hier macht Dickens auch nicht Halt einen brutalen Mord sehr bildhaft zu beschreiben, was ich bisher so überhaupt nicht von ihm kannte.\n" +
                                "Aber auch herzzerreißende und zu Tränen rührende Szenen sind inbegriffen, sowie melodramatische Szenen.\n" +
                                "Die Charaktere mögen zwar etwas überzeichnet und befremdlich sentimental wirken – die Guten sind unerträglich tugendhaft, die Bösen abgrundtief böse – aber auch dies trägt dazu bei, sich in die Zeit Charles Dickens versetzen zu lassen.\n" +
                                "\n" +
                                "Doch trotz durchaus stellenweise spannender Szenen, die an einen Thriller erinnern, besticht der Roman eher weniger durch den Plot, als durch die Thematisierung der damaligen Verhältnisse und Zustände der ärmlichen Bevölkerung und der Behandlung dieser durch die Gesellschaft. Dies erkennt man vor allem gegen Ende des Romans an der scheinbar wirren Zusammenführung der Handlungsstränge und der darauffolgenden Auflösung, mit der ich nicht ganz so glücklich bin.\n" +
                                "\n" +
                                "Die Übersetzung dieser Aufgabe durch Gustaf Meyrink (1868-1932 / Österreichischer Schriftsteller [„Der Golem“] und Übersetzer). ist meiner Meinung nach die beste der bisher von mir gelesenen. Und das, obwohl dieser sehr viel mehr Sozialkolorit einbringt als es Charles Dickens getan hat.\n" +
                                "Meyrink bringt verschiedene Dialekte ein wie z.B. die typische Gossen- und Gaunersprache, welche sehr starke Züge des österreichischen Dialekts aufweist, sowie den typischen „jiddischen“ Slang. Dies ließ mich zusätzlich zu Charles Dickens‘ Sarkasmus mehrmals schmunzeln.\n" +
                                "\n" +
                                "Diese Ausgabe enthält auch 24 Illustrationen der Erstausgabe von dem damals sehr bekannten Karikaturisten George Cruikshank (1792-1878).\n" +
                                "\n" +
                                "Die dtv-Ausgabe beinhaltet auch ein äußerst interessantes Nachwort bezüglich der Entstehungsgeschichte dieses Romans, sowie eine biographische Zeittafel von Charles Dickens. Ein ausschlaggebender Grund für mich mir immer öfter einen Klassiker aus diesem Verlag zu zulegen.\n" +
                                "\n" +
                                "Fazit:\n" +
                                "Ein wunderschöner Dickens-Klassiker, der einem lachen, weinen und mitfiebern lässt und so viel mehr enthält als nur die Geschichte eines armen Waisenjungen. Es ist sozialkritischer Realismus mit humoristischen Zügen, der einem zum Nachdenken anregt.\n" +
                                "Daher eine absolute Leseempfehlung für alle Klassik-Liebhaber und solche die es werden wollen. © Pink Anemone (Rezension mit Bildern)",
                        LocalDate.of(2017, 12, 10),
                        19,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Oliver Twist"),
                        users.get(7)),
                new Review("Oliver Twist",
                        "England 18: Oliver Twist kommt im Armenhaus zur Welt; seine Mutter stirbt bei der Geburt. Er kommt ins Waisenhaus, wird dort ohne Liebe aufgezogen und gerät schließlich in die Hände einer Diebesbande. Oliver muss viele Abenteuer bestehen und lernt viele ganz unterschiedliche Menschen kennen, bis er schließlich endlich erfährt, wer seine Eltern waren und wer er eigentlich ist.\n" +
                                "\n" +
                                "Diesen Klassiker wollte ich schon sehr lange lesen, habe mich aber nie so wirklich herangetraut. Nun habe ich aber endlich zu diesem Buch gegriffen und muss sagen, dass es mir sehr gut gefallen hat! Ich mochte die Geschichte als auch die Sprache! Die Sprache ist etwas altertümlich, was ich sehr mochte! Sie passte zur Geschichte. Eine zu moderne Übersetzung wäre hier unpassend gewesen.\n" +
                                "\n" +
                                "Die Geschichte von Oliver hat mir sehr gefallen und ich habe mit ihm mitfühlen können, obwohl Oliver für mich gar nicht wirklich im Vordergrund stand. Andere Figuren wurden hier zum Teil viel lebendiger beschrieben. Es war vielmehr die ganze Atmosphäre, die mich so packen konnte.\n" +
                                "\n" +
                                "Mir hat es viel Spaß gemacht, dieses Buch zu lesen und ich möchte unbedingt noch weitere Werke von Charles Dickens lesen!",
                        LocalDate.of(2018, 5, 16),
                        4,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Oliver Twist"),
                        users.get(5)),
                new Review("Literarisches Groß-Fresko",
                        "Uff, zweitausendeinhundert Seiten. Ich taste mich einmal in den ersten Band rein ... Das habe ich getan, um dann nahtlos zum zweiten zu greifen und bis zur letzten Seiten die Rostows, die Bolkonskis und die Kugarins auf ihren Wegen zu begleiten - ebenso wie die russischen und französischen Heerscharen auf ihren Marschrouten und Schlachtfeldern.\n" +
                                "\n" +
                                "Fesselnde Familienepen der Gewichtsklasse \"1000 Seiten plus\" finden sich auch anderweitig. Einige davon sind sprachlich wohl dichter und raffinierter geschrieben. Vermutlich die meisten münden in einen Schluss, der weniger süßlich ist. Aber nicht viele warten mit derart fein und plastisch gezeichneten - gerade auch weiblichen - Figuren auf. Und keines entrollt ein derart nahezu totales Zeit- und Sittenbild wie \"Krieg und Frieden\" für die russische Gesellschaft während der napoleonischen Kriege. Das St. Petersburger und Moskauer Salonleben in seinen unterschiedlichen Schattierungen rückt ebenso ins Bild wie die Cliquen des Hofes und der Armee, der militärische Alltag, die Angelegenheiten der feudalen Güterführung und die Freuden der Jagd am Land, familiäre Szenen, erotisches Werben und maskulines Gepränge. Der scheinbar mühelose Wechsel zwischen (militär)historisch penibel recherchierter Totale und intimer, fein beobachteter Nahaufnahme nährt wesentlich die Lesefreude an diesem literarischen Groß-Fresko.\n" +
                                "\n" +
                                "Dem Erzähler Lew Tolstoi vermag ich übrigens mehr abzugewinnen als dem Gesellschaftphilosophen und Militäranalytiker, dem er ebenfalls nicht zu knapp Raum gibt. Abgesehen von einem durchaus verzeihlichen anti-napoleonischen \"Bias\" vertritt er seine im Kern interessanten Überlegungen zu geschichtlichen Bewegkräften so streitbar, dass sie jedenfalls aus heutiger Sicht einen Zug ins Rigide und Krude annehmen. Natürlich ist da noch eine andere Beschränkung des Sichtwinkels, die beim Gutsbesitzers-Sohn Lew Tolstoi auffällt: Keine der \"normalen Familien\", die im Roman leben, lieben und ringen, könnte sich ein Dasein ohne Diener, Gouvernanten, Reitknechte usw. vorstellen. Russische Bauern oder Tagelöhner, also das Gros der damaligen Bevölkerung, wird nur in Statistenrollen und nur in der (durchaus überwiegend sympathisierenden bis verklärenden) Wahrnehmung der Oberschicht sichtbar. Das sehe ich aber als Ausdruck des damaligen \"Zeitgeists\" und nicht als Schwäche, die dem Autor anzulasten wäre. \"Krieg und Frieden\" steht zu Recht im Rang eines (ent)führenden, packenden und umfassenden Zeitbildes der russischen Gesellschaft im frühen 19. Jahrhundert.",
                        LocalDate.of(2021, 12, 23),
                        1,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Krieg und Frieden"),
                        users.get(4)),
                new Review("Alice im Wunderland ♦ Lewis Carroll | Rezension",
                        "Im Endeffekt tut die Sprache der Geschichte keinen Abbruch, denn Alice im Wunderland ist und bleibt ein Klassiker, auch für mich. Selbst wenn das Buch für mich nur 3 von 5 Sternen erhält, denn ich hab einfach ein wenig mehr von Alice als Charakter erwartet, ist es doch ein gutes Buch und ich habe jetzt auch so richtig Lust auf die letzte Verfilmung aus dem Jahre 2010.\n" +
                                "\n" +
                                "Ein besonderes Schmancker’l waren die Originalillustrationen von John Tenniel, die das Buch ein wenig aufgepeppt haben.",
                        LocalDate.of(2021, 8, 6),
                        7,
                        3,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Alice im Wunderland"),
                        users.get(3)),
                new Review("Tolles Retro-Buch",
                        "Alice im Wunderland war immer eine meiner liebsten Geschichten - das möchte ich natürlich an meine Tochter weitergeben.\n" +
                                "\n" +
                                "Das Buch hier ist klein und leicht zu halten, wenn das Kind auf dem Schoß sitzt und/oder einschläft. Die Seiten sind stabil und es enthält einige hübsche Zeichnungen.",
                        LocalDate.of(2020, 4, 26),
                        5,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Alice im Wunderland"),
                        users.get(0)),
                new Review("Ich liebs",
                        "Alice im Wunderland ist eins meiner liebsten Klassiker, welches ich immer wieder lesen könnte. Ob als Original Geschichte oder als Adaption.\n" +
                                "\n" +
                                "Wer wäre nicht als Kind dem weißen Hasen hinterher gelaufen? Ich auf alle Fälle. Disney hat Alice ein wenig freundliche erscheinen lassen, denn hier kann Alice schonmal ganz schön von oben herab sein können.\n" +
                                "Das Wunderland ist schon ein recht bizarrer und wundervoller Ort zu gleich. Und doch lockt er einen.\n" +
                                "Mal sehen wieviel Ausgaben dieses Klassikers noch bei mir einziehen.\n" +
                                "Schön ist hier noch, dass die Geschichte von Illustrationen begleitet werden.",
                        LocalDate.of(2021, 8, 22),
                        0,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Alice im Wunderland"),
                        users.get(1)),
                new Review("Nichts für Kinder! Unnötig brutal.",
                        "Dieses Buch ist sein Geld absolut nicht wert wenn man es für sein Kind haben will.\n" +
                                "\n" +
                                "Es ist nicht nur die klassische Geschichte vom Dschungelbuch enthalten ( die ganz okay ist, nur etwas abgespeckt und diese ist auch nicht sonderlich brutal obwohl sie natürlich den bekannten Verlauf nimmt), sondern auch andere kurze Geschichten und diese sind wirklich grausam.\n" +
                                "Als Beispiel geht es in der einen Geschichte um eine kleine Robbe die einen sicheren Ort sucht weil seine Freunde totgeprügelt wurden! ( und ja es wird deutlich beschrieben wie Menschen mit Knüppel auf die Robben einschlagen und das die überlebenden Robben das als normal empfinden weil die Menschen das immer machen, nur die kleine Robbe will das nicht hinnehmen...)\n" +
                                "\n" +
                                "Auch eine andere Geschichte geht um eine Schlangenfamilie die versucht Menschen zu töten und dann von einem Mungo getötet wird...\n" +
                                "\n" +
                                "In der letzten Geschichte geht es um Elefantenjäger und beschönigt das Elefanten von Menschen gefangen werden dürfen.\n" +
                                "\n" +
                                "Ich finde diese Geschichten absolut grausam und unpassend...was sollen Kinder den dabei vermittelt bekommen?!\n" +
                                "Es ist einfach nur brutal und somit meiner Meinung nach nichts für Kinder!\n" +
                                "\n" +
                                "Ich werde dieses Buch direkt wegwerfen und nach einem anständigen Dschungelbuch Buch suchen.",
                        LocalDate.of(2019, 11, 6),
                        22,
                        1,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Dschungelbuch"),
                        users.get(2)),
                new Review("Nur das halbe Buch handelt von Mogli. Die Kapitel sind zu lang. Eine Geschichte ist zu hart.",
                        "Wir haben uns dieses 5€ Buch gekauft, weil wir mit den anderen Büchern selber Art sehr zufrieden waren\n" +
                                "(Nils Holgerson, Peterchens Mondfahrt und Pinnochio. Im Ranking absteigend, aber alle gut)\n" +
                                "An sich hat uns die Geschichte vom Dschungelbuch gefallen.\n" +
                                "Allerdings sind die Kapitel zu lang und wir mussten in der Mitte eines Kapitels aufhören um am nächsten Tag weiter lesen. Das für sich alleine genommen wäre noch OK.\n" +
                                "Aber nur die ersten beiden Kapitel, ca. 1/3 des Buchs handelt vom Dschungelbuch.\n" +
                                "Die letzten 3 Kapitel, 2/3 des Buchs sind drei völlig andere Geschichten, die weder was mit dem Dschungelbuch, noch miteinander etwas zu tun haben. In einer Geschichte geht es um eine Robbe, zwei weitere Geschichten handeln von Tieren aus Indien.\n" +
                                "Die Robbe sucht nach einer neuen Heimat, weil viele ihrer Robbenfreunde jedes Jahr erschlagen werden...das fand ich dann doch etwas zu makaber.",
                        LocalDate.of(2021, 3, 19),
                        4,
                        2,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Dschungelbuch"),
                        users.get(3)),
                new Review("Klassiker der Jugend- und Abenteuerliteratur ungekürzt, noch immer lesenswert.",
                        "Die zwei Romane Mark Twains über die beiden jugendlichen Haudegen Tom Sawyer und Huck Finn sind mittlerweile Klassiker nicht nur der Jugendliteratur. Hier liegt eine ungekürzte, vollständige Übersetzung vor.\n" +
                                "Die Geschichten der beiden jugendlichen Helden sind immer noch aktuell und abenteuerlich und unterhalten auch heutzutage noch prächtig. Hinzu kommt mittlerweile der Blick zurück in das Leben vor ca. 130 Jahren. Absolut lesenswert und ein Standardwerk für jede Hausbibliothek.\n" +
                                "Das Buch selber ist stabil und als gebundenes Buch kann es auch länger in einem Regal stehen und mehrmals gelesen werden und immer noch gut aussehen.",
                        LocalDate.of(2021, 4, 28),
                        4,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Tom Sawyer"),
                        users.get(4)),
                new Review("Ein Buch voller Abenteuer",
                        "Wir haben „Tom Sawyers Abenteuer“ im Deutschunterricht in der fünften Klasse gelesen. Im Buch geht es hauptsächlich um zwei Jungen, Tom und Huck, im Alter von zwölf Jahren. Sie leben in einem kleinen Ort namens St. Petersburg und erleben dort jede Menge Abenteuer. Sie reißen z.B. von zu Hause aus, verlieben sich oder lernen Rauchen. Doch bei einer Schatzsuche läuft etwas aus dem Ruder und die Kinder stürzen sich in ein Abenteuer, dass nicht ohne ist. Da das Buch 1876 zum ersten Mal veröffentlicht wurde, sind die Wörter manchmal altmodisch, genauso wie der Erzählstil. Trotzdem hat der Autor oft unerwartet geschrieben, was mir sehr gut gefallen hat. Die Figuren und Orte wurden immer sehr detailreich beschrieben, sodass man sich alles bildlich vorstellen konnte. Ich finde die Ideen von Mark Twain gut und wollte immer gleich wissen, was als Nächstes passiert. Insgesamt fand ich das Buch gut, es ist praktisch, wenn man in der Schule ein Buch lesen will, weil man Kapitel gut neu oder weiter schrieben kann. Es ist aber auch ein gutes Buch für Menschen, die gerne selbst Abenteuer erleben oder lesen. Ich glaube, dass es vor allem für Kinder zwischen 9 und 13 geeignet ist, sie müssen natürlich Spannung mögen.\n" +
                                "\n" +
                                "Ich las das Buch im Deutschunterricht und fand wirklich großen Gefallen daran. Im Groben geht es um Tom Sawyer, der gerne Unruhe stiftet und mit seinen Freunden in St. Petersburg viele Abenteuer erlebt. Der Erzählstil ist sehr angenehm, da er zwar altertümlich erscheint, aber trotzdem auch mit der Zeit geht. Mir gefällt das Buch sehr gut, jedoch ist das Buch meiner Meinung nach nicht für Kinderunter 7 Jahren geeignet, aber für alle Menschen, die gerne Abenteuerromane lesen.",
                        LocalDate.of(2021, 6, 23),
                        2,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Tom Sawyer"),
                        users.get(5)),
                new Review("Skandalroman aus der Mottenkiste, der Rätsel aufgibt",
                        "Ich habe den \"Steppenwolf\" zum dritten Mal gelesen, und das Buch hat mir von Mal zu Mal weniger gefallen. Als junger Leser vor vielen Jahre war ich noch begeistert von dem Roman. Das ging nicht nur mir so. Hesses \"Steppenwolf\" wurde zu einer Ikone der Hippie-Bewegung, eine Band, die die Filmmusik zu \"Easy Rider\" beisteuerte, benannte sich nach dem Roman. Dieser landete im Giftschrank amerikanischer Bibliotheken, weil man die Jugend vor ihm beschützen wollte: die sicherste Möglichkeit, um eben diese Jugend für ein Buch zu interessieren. Schließlich bekam Hesse sogar den Nobelpreis für das Werk. All das deutet auf einen zeitlosen Klassiker hin. Umso schwerer ist es zu verstehen, dass der Roman beim Wiederlesen so blutleer und angestrengt wirkt, dass er eher Langeweile, als Aufregung und Aufruhr verbreitet.\n" +
                                "Der Roman enthält eine zentrale Botschaft, die der Autor dem Leser mit missionarischem Eifer immer und immer wieder vor Augen führt. Die menschliche Psyche ist kein abgeschlossenes Ganzes, kein von allem anderen getrenntes Individuum. Vielmehr beinhaltet sie viele widersprüchliche Persönlichkeiten: so wie der Protagonist Harry Haller, der in sich auch einen \"Steppenwolf\" trägt, einen wilden, antibürgerlichen, triebhaften Einzelgänger. Diese Erkenntnis war schon in den 20er Jahren, als Hesse das Buch schrieb, nicht mehr originell, heute pfeifen sie die Spatzen von den Dächern.\n" +
                                "Und der Skandal? All das, was in den Zwanzigern noch für Pfeffer und Exotik sorgte und die späteren Hippies ansprach, lockt heute auch keinen Hund mehr hinter dem Ofen vor: ein antibürgerlicher Affekt, der sich im Drogenkonsum und in wildem Sex äußert, geheimnisvolle fernöstliche Mystik, Tanz und \"Party\" (wie die heutigen Jugendlichen sagen würden) als Mittel der Erlösung, der Reiz der zwitterhaften \"Hermaphroditen\" (auch noch mit den Namen Hermann und Hermine), die (nur schamhaft angedeutete) Homoerotik usw.\n" +
                                "Man kann es einem Buch nicht zum Vorwurf machen, dass sein Thema irgendwann überholt ist, aber man kann ihm vorwerfen, dass es schlecht geschrieben ist, und genau das trifft hier zu. Der Autor vertraut den literarischen Mitteln nicht, um seine Botschaft zu verkünden, stattdessen wird immer wieder \"Klartext\" gesprochen. Die einzelnen Figuren erklären immer wieder langatmig, was es mit Harrys Steppenwolf-Natur auf sich hat. Der Gipfel ist dann das \"Traktat vom Steppenwolf\", eine \"Lektürehilfe\", die in den Roman integriert ist.\n" +
                                "\"Der Steppenwolf\" ist gerade Pflichtlektüre für die Abiturienten in Baden-Württemberg. Ob man junge Menschen, die meist nur noch widerwillige Leser sind, so für Literatur begeistern kann? Es bleiben Zweifel.",
                        LocalDate.of(2019, 8, 14),
                        24,
                        1,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Steppenwolf"),
                        users.get(6)),
                new Review("Ein zeitloses Bild des Menschen. Ein Roman, der zu den 100 wichtigsten Werken der Weltlieratur gehört.",
                        "Was soll ein (unprofessioneller) Liebhaber der Literatur, ein Dilettant aus dem literaturfernen Gebiet der Naturwissenschaften,der sich durch die Weltliteratur zu lesen versucht, zu Herrmann Hesse und seinem „Steppenwolf“ sagen? Alles ist gesagt, alles ist nachzulesen. Es gibt zahlreiche Interpretationshilfen ( die man als Normalleser nicht benötigt). Alle klugen Worte wären abgeschrieben.\n" +
                                "Bekanntlich geht es um die „Zerrissenheit“ der menschlichen Seele, der Wolf und der angepasste (Spieß)-Bürger. Sie hassen sich und sie bekämpfen sich. „Zwei Seelen wohnen, ach! In meiner Brust, Die eine will sich von der andern trennen…“ sagt Goethe`s Faust.\n" +
                                "Der 1927 erschienene Roman ist absolut zeitgemäß. Der Überdruss an der damals wie heute faden und oberflächlichen Welt des Massenkonsums, der dummen, geistlosen Werbung, der gelenkten Vergnügungen, der Kultur der Verflachung, der Verehrung von irgendwelchen, heute würde man sagen, Pop- oder Sportgrößen, wird dargestellt.\n" +
                                "Schüler der Oberstufe oder Studenten der Literatur müssen dieses Werk pflichtgemäß lesen und vielleicht ein Referat oder „Aufsatz“ darüber verfassen.\n" +
                                "Sollte man sich als interessierter Laie mit diesem angeblich so schwierigen Werk befassen?\n" +
                                "Ja, unbedingt! Es ist gut lesbar, man schaut in tief in die menschliche Seele hinein, es ist ein Lesegenuss!",
                        LocalDate.of(2017, 4, 27),
                        24,
                        1,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Steppenwolf"),
                        users.get(7)),
                new Review("Eine lange Lesereise",
                        "Der Weg ist das Ziel, behaupten die Weisen. Und wahrscheinlich liegen sie damit genau richtig. Bei Musil trifft diese Weisheit jedenfalls den Kern des Lesegenusses, denn nicht die Geschichte im eigentlichen Sinne vermag es zu verzaubern, es sind Musils Satzkonstruktionen und Gedankengänge, die sich beim Lesen entfalten wie ein herrliches Karamellbonbon, das sich, Schicht um Schicht, zu einem wahren Hochgenuss entwickelt.",
                        LocalDate.of(2020, 2, 15),
                        25,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Mann ohne Eigenschaften"),
                        users.get(2)),
                new Review("\"Der Mann ohne Eigenschaften\"",
                        "\"Der Mann ohne Eigenschaften\" von Musil ist einer der wichtigsten Romane des 20. Jahrhunderts. Ich hatte ihn vor Jahren einmal in einer Zeitschrift als Fortsetzungsserie gelesen und hatte nun das Bedürfnis, den Roman noch einmal zu lesen. Deshalb habe ich das Buch bestellt. Es kam schnell , aber ich lese noch daran. Manuela Peters",
                        LocalDate.of(2021, 4, 28),
                        3,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Mann ohne Eigenschaften"),
                        users.get(4)),
                new Review("Immer noch provozierend...",
                        "An diesem Schlüsseltext des Existenzialismus kommt man nicht vorbei und sollte es auch nicht. Auch ohne sich mit der philosophischen Idee theoretisch auseinanderzusetzen, begreift man bei der Lektüre ihre Grundsätze schon ansatzweise, allein beim Betrachten der inneren Verfasstheit des Protagonisten. Dem Leben fremd, zurückgeworfen auf die eigene Existenz, mit der Gleichgültigkeit die entsteht, wenn man religiöse, ideologische, konventionelle Sinnbegründungen ablehnt und am Kreieren eines individuellen Lebenssinns scheitert. Obwohl Rock und Punk und Grunge und alle möglichen anderen \"schockierenden\" Jugend- und Subkulturen durchs Land gezogen sind seither, verwundert es doch immer noch, wenn man sich selbst dabei erwischt, wie sehr einen die eigentlich recht simple, bloß konsequent gleichgültige Haltung der Hauptfigur provoziert und verstört.\n" +
                                "Starker Text, eingedampft aufs Wesentliche, bildend und lehrreich. Sollte man gelesen haben.",
                        LocalDate.of(2018, 12, 28),
                        39,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Fremde"),
                        users.get(3)),
                new Review("Ein gleichgültiges Leben",
                        "Albert Camus‘ »Der Fremde« lässt sich sehr schön literarisch, ebenso gut aber auch zur Unterhaltung lesen. Die Geschichte spielt in Algerien zur Zeit des Zweiten Weltkriegs; als Leser begleitet man einen jungen Mann, der – wenn auch unfreiwillig - nicht ganz in die ihm gesellschaftlich zugeordneten Rollen passt und Konventionen unbemerkt nicht entspricht. Vielem steht er gleichgültig gegenüber, dem man – »Das gehört sich so« – nicht gleichgültig gegenüberstehen sollte. Andere Dinge empfindet er einfach anders. Durch einen unglücklichen Zufall wird er zum Mörder – seine Andersartigkeit ist ihm bei der sich anschließenden Gerichtsverhandlung keine Hilfe.\n" +
                                "\n" +
                                "Camus schreibt in kurzen und leidenschaftslosen Sätzen, die einen als Leser gut durch die Geschichte tragen und die darüber hinaus sehr gut zur Gleichgültigkeit des Protagonisten passen. Der Leser darf das Geschehen durch eine gewisse Distanz betrachten, was es aber kein Stück uninteressanter macht.\n" +
                                "\n" +
                                "Etwas irritierend ist lediglich der unkonventionelle Gebrauch der verschiedenen Zeitformen – Perfekt, Präteritum, Plusquamperfekt; alles wird wild durcheinander gewürfelt, vorrangig das sonst selten zu lesende Perfekt. Das empfand ich besonders zu Beginn etwas als Hürde; ich kann aber nicht sagen, ob das schlicht an der Übersetzung liegt oder damit irgendeine Eigenheit des Erzählstils aus dem Original vermittelt werden soll.\n" +
                                "\n" +
                                "Wer sich im Anschluss näher mit dem Inhalt beschäftigen möchte, wird für die ersten Schritte bereits im Netz ausreichend fündig.",
                        LocalDate.of(2019, 4, 28),
                        11,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Der Fremde"),
                        users.get(1)),
                new Review("Lohnenswerter Klassiker!",
                        "Von diesem Klassiker der Fantasy-Literatur hatte ich schon viel gehört. Und jetzt bin ich auch endlich dazu gekommen, ihn zu lesen! Zugegeben: Dazu braucht man ein bisschen Zeit und, ja, auch Geduld. So gesehen entspricht das Buch ganz sicher nicht dem aktuellen Zeitgeist ...\n" +
                                "\n" +
                                "Aber: Wer sich für historische Fantasy und den Tafelrunden-Mythos interessiert und über die eine oder andere Länge (vor allem in Bezug auf Glaubensfragen) hinwegsieht, wird mit einer tollen Atmosphäre und einer vielschichtigen, anspruchsvollen Geschichte belohnt!",
                        LocalDate.of(2019, 10, 10),
                        5,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Nebel von Avalon"),
                        users.get(0)),
                new Review("Super",
                        "Eines der besten Fantasy Bücher ever. Muss man gelesen haben.",
                        LocalDate.of(2021, 4, 12),
                        2,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Nebel von Avalon"),
                        users.get(5)),
                new Review("Must read",
                        "Dieses Buch wurde mir von einem Bekannten empfohlen. Es hat mich Mühe und Konzentration gekostet beim Lesen. Und vielleicht bin ich intellektuell nicht auf der Höhe.\n" +
                                "\n" +
                                "Doch das was ich verstehen konnte, hat mich beeindruckt aufgrund der unerschrockenen Betrachtungen und Beschreibungen des kapitalistischen - „immer mehr, schneller, weiter“ - Arbeitsklima. Dass es bis zum 19. Jahrhundert Kinderarbeit gab wusste ich auch nicht.\n" +
                                "\n" +
                                "Beziehungsweise wurde es uns in der Schule erzählt und ich wurde dank diesem Buch wieder daran erinnert.\n" +
                                "\n" +
                                "Ich glaube ja fest daran dass in unserem Zeitalter ein spirituelles Rangehen ans Leben uns von den negativen Aspekten des Kapitalismus befreien wird.",
                        LocalDate.of(2021, 4, 15),
                        5,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Kapital"),
                        users.get(6)),
                new Review("Das Karlpital",
                        "Das einzige, was mich beim Lesen immer wieder gestört hat: Er hätte das Buch auch \"Das Karlpital\" nennen können.",
                        LocalDate.of(2020, 4, 26),
                        105,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Das Kapital"),
                        users.get(7)),
                new Review("Tadelloses Werk!",
                        "Enthält nützliche Hinweise zum Verständnis sowie notwendige Übersichts Kapitel.\n" +
                                "Lesenswert auch die Epitome sowie die Zeugnisse der Zeitgenossen. Die Bild Kommentare sind gelegentlich an der Grenze zur Frivolität.\n" +
                                "Insgesamt aber eine vorzügliche Ergänzung zu der gesamt Übersetzung.",
                        LocalDate.of(2017, 6, 12),
                        0,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Verfall und Untergang"),
                        users.get(1)),
                new Review("Leider nicht meins.",
                        "Ich habe es mir aus Interesse gekauft und versucht, es zu lesen, habe mich aber recht schnell gelangweilt, da es doch recht langatmig ist. Na ja, ist wohl nichts für mich, aber einen Versuch war es wert. Vielleicht werde ich es eines Tages doch noch mal zur Hand nehmen. Leider sind die Seiten auch recht eng bedruckt und die Buchstaben ziemlich klein, was das Lesen noch anstrengender macht, als es bei dieser Art von Buch sowieso schon ist. Da es ein Klassiker, den man wohl im Regal stehen haben sollte, ist und diese Ausgabe für den Preis qualitativ relativ hochwertig wirkt, bekommt es von mir 4 Sterne.",
                        LocalDate.of(2021, 6, 21),
                        1,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Die Welt als Wille"),
                        users.get(0)),
                new Review("Spannende Verknüpfung von Roman und philosophischem Sachbuch",
                        "Vor einigen Jahren bin ich zum ersten Mal auf das Buch »Sofies Welt« gestoßen und wollte es unbedingt mal lesen. Nun habe ich es endlich geschafft, muss aber sagen, dass es einige Durststrecken mit sich brachte und letztlich doch ziemlich gezogen hat.\n" +
                                "\n" +
                                "Sofie erhält Briefe eines Unbekannten, in denen der Freme DIE Fragen des Lebens an sie stellt. Im Folgenden wird er ihr Philosophielehrer, der sie mit jedem Brief, mit jedem Treffen weiter in diese wissenschaftliche, so interessante Disziplin einführt – und damit ihr kleines, behütetes Leben in einer norwegischen Kleinstadt ziemlich ins Wanken bringt.\n" +
                                "\n" +
                                "Nach den ersten Kapiteln scheint es erst so, als würde einen das Buch behutsam dazu inspirieren, sich selbst mal wieder mit diesen Fragen auseinanderzusetzen. Dieses Gefühl tritt jedoch bald stark in den Hintergrund. Natürlich kann man von sich aus damit anfangen; das Buch selbst setzt aber ganz andere Schwerpunkte, die bereits im Untertitel angesprochen werden: Hier geht es vorrangig um die Geschichte der Philosophie. Einmal quer durch die Geschichte. Diese Nachhilfestunden lesen sich tatsächlich ziemlich genau wie in einem Sachbuch und sie machen etwa die Hälfte, wenn nicht mehr, des Buches aus. Muss man mögen. Dann ist es anschaulich erklärt. Die »Lektionen«, die Sofie lernt, werden anschließend auch häufig in die Rahmenhandlung eingebunden: in Sofies Welt und ihr Leben.\n" +
                                "\n" +
                                "Zuerst scheint die Rahmenhandlung eher ein nötiges Übel zu sein, damit das Buch als Roman durchgeht. Im Laufe des Buches hält die Geschichte aber die eine oder andere spannende Wendung bereit, die auch diesen Teil der Geschichte absolut lesenswert macht und auch die Themen des Philosophiekurses nicht verfehlt.\n" +
                                "\n" +
                                "Wer also einen langen Atem hat, der wird letztlich mit einer spannenden, interessanten und lehrreichen Geschichte belohnt. Meines Erachtens absolut nicht nur für junge Leser geeignet und nützlich.",
                        LocalDate.of(2018, 12, 31),
                        30,
                        4,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Sofies Welt"),
                        users.get(2)),
                new Review("Ein wirklich gutes Buch!",
                        "Kann man die 3000 jährige Geschichte der Philosophie tatsälich spannend erklären? Ja, es geht tatsächlich. Der Autor hat es geschafft, einen unterhaltsamen und spannenden Roman zu schreiben, bei dem er bei den Griechen anfangend, Zusammenhänge erklärt, zeigt wie alle Theorien aufeinander aufgebaut sind, von einander profitiert haben und sich bis heute weiter entwicklelt haben.\n" +
                                "\n" +
                                "Das ganze so geschrieben, dass man das Buch gar nicht mehr aus der Hand legen möchte. Top. Eines der besten Sachbücher, die ich je in den Händen hatte.",
                        LocalDate.of(2018, 3, 5),
                        20,
                        5,
                        bookRepository.findFirstByTitleContainingIgnoreCase("Sofies Welt"),
                        users.get(6))
        ));

        Discussion d1 = new Discussion(
                "Eine durch und durch bezaubernde Reise",
                "Was habt ihr über die Welt der Philosophie herausgefunden?",
                LocalDate.of(2010, 6, 8),
                users.get(1),
                bookRepository.findFirstByTitleContainingIgnoreCase("Sofies Welt"),
                null);

        d1.setComments(List.of(
                new Comment("Was ist der Mensch?Woher kommt die Welt? Gleichsam verwundert und neugierig, läßt sich Sofie auf diese \"Lehrstunden\" ein... faziniert folgt sie den Worten, die sie durch das Reich der Philosophie und durch unsere Weltgeschichte führen und ihre Begeisterung kennt bald keine Grenzen mehr!",
                        LocalDateTime.of(2010, 7, 2, 8, 0),
                        users.get(0),
                        d1)
        ));

        Discussion d2 = new Discussion(
                "Das Antlitz eines Herrschers würdig",
                "Die Kleidung ist hervorragend beschrieben und illustriert.",
                LocalDate.of(2019, 12, 8),
                users.get(7),
                bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 9"),
                null);

        d2.setComments(List.of(
                new Comment("Ja, das Gewand ist wahrlich ausgezeichnet gewählt!",
                        LocalDateTime.of(2019, 12, 9, 12, 0),
                        users.get(1),
                        d2),
                new Comment("Da muss ich aber widersprechen! Das Gewand ist nicht eines Herrschers würdig!",
                        LocalDateTime.of(2020, 1, 5, 12, 0),
                        users.get(3),
                        d2)
        ));
        new Review(null, null, null, null, 1, bookRepository.findById(1L).get(), null);
        Discussion d3 = new Discussion(
                "Das Antlitz eines Herrschers würdig",
                "Die Kleidung ist hervorragend beschrieben und illustriert.",
                LocalDate.of(2019, 12, 8),
                users.get(7),
                bookRepository.findFirstByTitleContainingIgnoreCase("Overlord, Vol. 10"),
                null);

        d3.setComments(List.of(
                new Comment("Ja, das Gewand ist wahrlich ausgezeichnet gewählt!",
                        LocalDateTime.of(2019, 12, 9, 12, 0),
                        users.get(2),
                        d3),
                new Comment("Da muss ich aber widersprechen! Das Gewand ist nicht eines Herrschers würdig!",
                        LocalDateTime.of(2020, 1, 5, 12, 0),
                        users.get(3),
                        d3)
        ));

        Discussion d4 = new Discussion(
                "Etwas verwirrend",
                "Zwar hervorragend geschrieben, jedoch konnte ich der Geschichte ab Kapitel 8 nur schwer folgen.",
                LocalDate.of(2017, 3, 20),
                users.get(6),
                bookRepository.findFirstByTitleContainingIgnoreCase("Metamorphosen"),
                null);

        d4.setComments(List.of(
                new Comment("Ja stimmt, hatte da auch Schwierigkeiten - möglicherweise hatte der Autor ein Zeitproblem?",
                        LocalDateTime.of(2017, 3, 25, 12, 0),
                        users.get(4),
                        d4),
                new Comment("Da muss ich aber widersprechen! Die Handlung und die Erzählung werden mit jeder Seite ausgeklügelter!",
                        LocalDateTime.of(2017, 4, 5, 12, 0),
                        users.get(0),
                        d4)
        ));

        Discussion d5 = new Discussion(
                "Empfehlungen für Bücher wie dieses?",
                "Hey, ich bin auf der Suche nach Büchern, die wie Metamorphosen geschrieben sind, weil ich Philosophie und Geschichte liebe. Danke im Voraus!",
                LocalDate.of(2022, 3, 20),
                users.get(5),
                bookRepository.findFirstByTitleContainingIgnoreCase("Metamorphosen"),
                null);

        d5.setComments(List.of(
                new Comment("Möglicherweise würde dir Diogenes Geschichte gefallen.",
                        LocalDateTime.of(2022, 3, 27, 12, 0),
                        users.get(7),
                        d5)
        ));

        Discussion d6 = new Discussion(
                "Leider nicht mehr wie früher",
                "Ich finde Romeo und Julia im Vergleich zur jetzigen Literatur schwach. Was meint ihr?",
                LocalDate.of(2022, 2, 4),
                users.get(4),
                bookRepository.findFirstByTitleContainingIgnoreCase("Rome und Julia"),
                null);

        d6.setComments(List.of(
                new Comment("Nein, nein! Romeo und Julia ist ein Kanon und jede(r), der/die etwas anderes behauptet, hat einen schlechten Geschmack!",
                        LocalDateTime.of(2022, 3, 9, 12, 0),
                        users.get(2),
                        d6),
                new Comment("Ja, finde ich auch. Vielleicht liegt es aber auch daran, dass ich die Geschichte bereits 20 mal gehört bzw. gelesen habe.",
                        LocalDateTime.of(2022, 2, 6, 12, 0),
                        users.get(3),
                        d6)
        ));

        Discussion d7 = new Discussion(
                "So weise war er doch gar nicht, oder?",
                "Ich meine, so weise war Nathan doch gar nicht, oder? Wieso ist das der Buchtitel?",
                LocalDate.of(2021, 6, 21),
                users.get(2),
                bookRepository.findFirstByTitleContainingIgnoreCase("Nathan der Weise"),
                null
        );

        d7.setComments(List.of(
                new Comment("Vielleicht sollten Sie das Buch lesen, bevor Sie Diskussionen erstellen!",
                        LocalDateTime.of(2021, 12, 9, 12, 0),
                        users.get(6),
                        d7)
        ));

        Discussion d8 = new Discussion(
                "Welches Jahr?",
                "Weiß jemand in welchem Jahr das spielt? Ich habe versucht das zu recherchieren und konnte leider nichts finden. Brauche es dringend für ein Referat!",
                LocalDate.now(),
                users.get(3),
                bookRepository.findFirstByTitleContainingIgnoreCase("Oliver Twist"),
                null
        );

        Discussion d9 = new Discussion(
                "Passendes Buch für 12 Nichte?",
                "Hallo,\n\ndenkt ihr, dass das Buch meiner 12 jährigen Nichte gefallen würde?",
                LocalDate.now(),
                users.get(0),
                bookRepository.findFirstByTitleContainingIgnoreCase("Das Dschungelbuch"),
                null
        );

        Discussion d10 = new Discussion(
                "Passendes Buch für 12 Nichte?",
                "Hallo,\n\ndenkt ihr, dass das Buch meiner 12 jährigen Nichte gefallen würde?",
                LocalDate.now(),
                users.get(0),
                bookRepository.findFirstByTitleContainingIgnoreCase("Alice im Wunderland"),
                null
        );

        Discussion d11 = new Discussion(
                "Ein ziemlich frecher Kerl dieser ...",
                "Hallo,\n\nTom Sawyer ist schon ein unverschämter Bur. Finn sollte es eigentlich besser wissen, als sich mit ihm abzugeben!",
                LocalDate.of(2018, 1, 3),
                users.get(1),
                bookRepository.findFirstByTitleContainingIgnoreCase("Tom Sawyer"),
                null
        );

        d11.setComments(List.of(
                new Comment("Tom Sawyer ist auf jeden Fall keine sich vorbildlich verhaltende Person!",
                        LocalDateTime.of(2019, 12, 9, 12, 0),
                        users.get(5),
                        d11),
                new Comment("Er weiß sich nur gut zu verkaufen und geht Schwierigkeiten souverän aus dem Weg.",
                        LocalDateTime.of(2020, 1, 5, 12, 0),
                        users.get(6),
                        d11)
        ));

        Discussion d12 = new Discussion(
                "Lohnt sich der fette Schinken?",
                "Heyho,\n\nich bin derzeit auf der Suche nach etwas neuem und bin auf \"Die Nebel von Avalon\" gestoßen. Mich schreckt derzeit nur die Buchdicke davon ab, da ich nicht weiß ob ich genug Zeit habe, so lange an einem Buch zu sitzen. Was sagt ihr?",
                LocalDate.of(2019, 1, 3),
                users.get(6),
                bookRepository.findFirstByTitleContainingIgnoreCase("Die Nebel von Avalon"),
                null
        );

        d12.setComments(List.of(
                new Comment("Es lohnt sich definitv, jedoch wird es erst ab dem 3. Kapitel wirklich interessant!",
                        LocalDateTime.of(2019, 4, 2, 12, 0),
                        users.get(0),
                        d3),
                new Comment("Ich finde, dass es sich auszahlt.",
                        LocalDateTime.of(2020, 10, 28, 12, 0),
                        users.get(7),
                        d3)
        ));

        Discussion d13 = new Discussion(
                "Für einen 16 jährigen Jugendlichen geeignet?",
                "Hey,\n\nIch bin gerade auf der Suche nach etwas für meinen 16 jährigen Sohn. Ich habe zwar Krieg und Frieden gelesen, nur bin ich mir unsicher, ob es meinem Sohn gefallen würde.",
                LocalDate.of(2019, 3, 3),
                users.get(2),
                bookRepository.findFirstByTitleContainingIgnoreCase("Krieg und Frieden"),
                null
        );

        d13.setComments(List.of(
                new Comment("Wenn er bereits reif genug ist, können Sie ihm gerne schenken... oder Sie versuchen es einfach mit einem herkömmlichen Roman für Jugendliche",
                        LocalDateTime.of(2019, 10, 7, 12, 0),
                        users.get(2),
                        d13),
                new Comment("Ich würde es nicht empfehlen",
                        LocalDateTime.of(2019, 4, 21, 12, 0),
                        users.get(3),
                        d13)
        ));


        discussionRepository.saveAll(List.of(
                d1,
                d2,
                d3,
                d4,
                d5,
                d6,
                d7,
                d8,
                d9,
                d10,
                d11,
                d12,
                d13
        ));


        try {

            Resource resource = new ClassPathResource("rating_data.csv");
            InputStream input = resource.getInputStream();
            File file = resource.getFile();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(",");
                if (tempArr.length != 2) {
                    continue;
                }
                Optional<Book> b = bookRepository.findById(Long.parseLong(tempArr[1]));
                if (b.isEmpty()) {
                    continue;
                }
                reviewRepository.save(new Review(null, null, null, null,
                        Integer.parseInt(tempArr[0]),
                        b.get(),
                        null));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Book> books = bookRepository.findAll();
        for (Book b: books) {
            List<Review> reviews = reviewRepository.findAllByBookId(b.getId());
            Long stars = 0L;
            for (Review r: reviews) {
                stars += r.getRating();
            }
            if (stars > 0) {
                b.setStars(stars);
                b.setNumberOfReviews((long) reviews.size());
                bookRepository.save(b);
            }
        }

    }

}
