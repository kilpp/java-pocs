import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import provider.SystemArgumentProvider;
import usecase.CaseService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(MockitoExtension.class)
public class TestPoc {
    @InjectMocks
    private CaseService service;

    @ParameterizedTest
    @ValueSource(strings = "3")
    void isItOdd(Integer argument) {
        assertFalse(service.isEven(argument));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6})
    void isEven(Integer number) {
        assertTrue(service.isEven(number));
    }

    @Test
    void isOddAndNotEven() {
        Integer odd = 1;
        Integer even = 2;
        assertAll("Is odd and even",
                () -> assertFalse(service.isEven(odd)),
                () -> assertTrue(service.isEven(even))
        );
    }

    @DisplayName("Should fail if odd")
    @ParameterizedTest(name = "{0} odd")
    @ValueSource(ints = {1, 3})
    void oddNotEven(int year) {
        assertFalse(service.isEven(year));
    }

    @RepeatedTest(value = 1, name = "{displayName} - {currentRepetition}")
    @DisplayName("Repeating")
    void repeat(TestInfo testInfo) {
        assertEquals(testInfo.getDisplayName(), "Repeating - 1");
    }

    @TestFactory
    Iterable<DynamicTest> testDinamicEven() {
        return Collections.singletonList(
                dynamicTest("Is it even", () -> assertTrue(service.isEven(2)))
        );
    }

    @Disabled("BUG")
    @Test
    void bugged() {
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testOnLinux() {
        assertEquals(System.getProperty("os.name"), "Linux");
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_9)
    void testOnJava8() {
        assertEquals(System.getProperty("java.version"), "1.8.0_352");
    }

    @Test
    @EnabledIfSystemProperty(named = "file.encoding", matches = "UTF-8")
    void testOnFileEncoding() {
        assertEquals(System.getProperty("file.encoding"), "UTF-8");
    }

    @ParameterizedTest
    @MethodSource("systemProperties")
    void testWithSystemProperties(String value) {
        assertNotNull(value);
    }

    @ParameterizedTest
    @ArgumentsSource(SystemArgumentProvider.class)
    void testWithArgumentsSource(String argument) {
        assertNotNull(argument);
    }

    static Stream<String> systemProperties() {
        return System.getProperties().values().stream().map(String::valueOf);
    }

    @ParameterizedTest(name = "{index} ==> name ''{0}'' value is ''{1}''")
    @CsvFileSource(resources = "test.csv", numLinesToSkip = 1, useHeadersInDisplayName = false)
    void testCsvFile(String name, int value) {
        assertEquals("test", name);
        assertEquals(1, value);
    }

    @ParameterizedTest(name = "{index} ==> value ''{0}''")
    @CsvFileSource(resources = "csv-zipcodes.csv", numLinesToSkip = 1, useHeadersInDisplayName = false)
    void testCsvZipCodeFile(long value) {
        System.out.println(value);
        assertNotNull(value);
    }

//    @ParameterizedTest(name = "{index} ==> name ''{0}'' value is ''{1}''")
//    @CsvFileSource(resources = "test.csv", numLinesToSkip = 1, useHeadersInDisplayName = false)
//    void testCsvFileProvider(@CsvToPair Pair<String, String> pair) {
//        assertEquals("test", pair.getKey());
//        assertEquals("1", pair.getValue());
//    }

    @Test
    public void haveDuplicate() {
        Boolean isThere = getNewList().stream().anyMatch(a -> getFullList().contains(a));
        assertFalse(isThere);
    }

    public List<Integer> getFullList() {
        return Arrays.asList(33503,
                33508,
                33509,
                33510,
                33511,
                33527,
                33530,
                33534,
                33547,
                33548,
                33549,
                33550,
                33556,
                33558,
                33559,
                33563,
                33564,
                33565,
                33566,
                33567,
                33568,
                33569,
                33570,
                33571,
                33572,
                33573,
                33575,
                33578,
                33579,
                33583,
                33584,
                33586,
                33587,
                33592,
                33594,
                33595,
                33596,
                33598,
                33601,
                33602,
                33603,
                33604,
                33605,
                33606,
                33607,
                33608,
                33609,
                33610,
                33611,
                33612,
                33613,
                33614,
                33615,
                33616,
                33617,
                33618,
                33619,
                33620,
                33621,
                33622,
                33623,
                33624,
                33625,
                33626,
                33629,
                33630,
                33631,
                33633,
                33634,
                33635,
                33637,
                33646,
                33647,
                33650,
                33655,
                33660,
                33661,
                33662,
                33663,
                33664,
                33672,
                33673,
                33674,
                33675,
                33677,
                33679,
                33680,
                33681,
                33682,
                33684,
                33685,
                33686,
                33687,
                33688,
                33689,
                33694,
                33701,
                33702,
                33703,
                33704,
                33705,
                33706,
                33707,
                33708,
                33709,
                33710,
                33711,
                33712,
                33713,
                33714,
                33715,
                33716,
                33729,
                33730,
                33731,
                33732,
                33733,
                33734,
                33736,
                33738,
                33740,
                33741,
                33742,
                33743,
                33744,
                33747,
                33755,
                33756,
                33757,
                33758,
                33759,
                33760,
                33761,
                33762,
                33763,
                33764,
                33765,
                33766,
                33767,
                33769,
                33770,
                33771,
                33772,
                33773,
                33774,
                33775,
                33776,
                33777,
                33778,
                33779,
                33780,
                33781,
                33782,
                33784,
                33785,
                33786,
                33834,
                33865,
                33873,
                33890,
                33901,
                33902,
                33903,
                33904,
                33905,
                33906,
                33907,
                33908,
                33909,
                33910,
                33911,
                33912,
                33913,
                33914,
                33915,
                33916,
                33917,
                33918,
                33919,
                33920,
                33921,
                33922,
                33924,
                33927,
                33928,
                33929,
                33931,
                33932,
                33936,
                33938,
                33945,
                33946,
                33947,
                33948,
                33949,
                33950,
                33951,
                33952,
                33953,
                33954,
                33955,
                33956,
                33957,
                33965,
                33966,
                33967,
                33970,
                33971,
                33972,
                33973,
                33974,
                33976,
                33980,
                33981,
                33982,
                33983,
                33990,
                33991,
                33993,
                33994,
                34101,
                34102,
                34103,
                34104,
                34105,
                34106,
                34107,
                34108,
                34109,
                34110,
                34112,
                34113,
                34114,
                34116,
                34117,
                34119,
                34120,
                34133,
                34134,
                34135,
                34136,
                34137,
                34138,
                34139,
                34140,
                34141,
                34142,
                34143,
                34145,
                34146,
                34201,
                34202,
                34203,
                34204,
                34205,
                34206,
                34207,
                34208,
                34209,
                34210,
                34211,
                34212,
                34215,
                34216,
                34217,
                34218,
                34219,
                34220,
                34221,
                34222,
                34223,
                34224,
                34228,
                34229,
                34230,
                34231,
                34232,
                34233,
                34234,
                34235,
                34236,
                34237,
                34238,
                34239,
                34240,
                34241,
                34242,
                34243,
                34249,
                34250,
                34251,
                34260,
                34264,
                34265,
                34266,
                34267,
                34268,
                34269,
                34270,
                34272,
                34274,
                34275,
                34276,
                34277,
                34278,
                34280,
                34281,
                34282,
                34284,
                34285,
                34286,
                34287,
                34288,
                34289,
                34290,
                34291,
                34292,
                34293,
                34295,
                34660,
                34677,
                34681,
                34682,
                34683,
                34684,
                34685,
                34688,
                34689,
                34695,
                34697,
                34698,
                32701,
                32703,
                32704,
                32707,
                32708,
                32709,
                32710,
                32712,
                32714,
                32715,
                32716,
                32718,
                32719,
                32730,
                32732,
                32733,
                32745,
                32746,
                32747,
                32750,
                32751,
                32752,
                32762,
                32765,
                32766,
                32768,
                32771,
                32772,
                32773,
                32777,
                32779,
                32789,
                32790,
                32791,
                32792,
                32793,
                32794,
                32795,
                32798,
                32799,
                32801,
                32802,
                32803,
                32804,
                32805,
                32806,
                32807,
                32808,
                32809,
                32810,
                32811,
                32812,
                32814,
                32816,
                32817,
                32818,
                32819,
                32820,
                32821,
                32822,
                32824,
                32825,
                32826,
                32827,
                32828,
                32829,
                32830,
                32831,
                32832,
                32833,
                32834,
                32835,
                32836,
                32837,
                32839,
                32853,
                32854,
                32855,
                32856,
                32857,
                32858,
                32859,
                32860,
                32861,
                32862,
                32867,
                32868,
                32869,
                32872,
                32877,
                32878,
                32885,
                32886,
                32887,
                32891,
                32896,
                32897,
                33801,
                33802,
                33803,
                33804,
                33805,
                33806,
                33807,
                33809,
                33810,
                33811,
                33812,
                33813,
                33815,
                33820,
                33823,
                33827,
                33830,
                33831,
                33835,
                33836,
                33837,
                33838,
                33839,
                33840,
                33841,
                33843,
                33844,
                33845,
                33846,
                33847,
                33848,
                33849,
                33850,
                33851,
                33853,
                33854,
                33855,
                33856,
                33858,
                33859,
                33860,
                33863,
                33867,
                33868,
                33877,
                33880,
                33881,
                33882,
                33883,
                33884,
                33885,
                33888,
                33896,
                33897,
                33898,
                34734,
                34739,
                34740,
                34741,
                34742,
                34743,
                34744,
                34745,
                34746,
                34747,
                34758,
                34759,
                34760,
                34761,
                34769,
                34770,
                34771,
                34772,
                34773,
                34777,
                34778,
                34786,
                34787);
    }

    public List<Integer> getNewList() {
        return Arrays.asList(
                32004,
                32007,
                32033,
                32080,
                32081,
                32082,
                32084,
                32085,
                32086,
                32092,
                32095,
                32105,
                32110,
                32112,
                32114,
                32115,
                32116,
                32117,
                32118,
                32119,
                32120,
                32121,
                32122,
                32123,
                32124,
                32125,
                32126,
                32127,
                32128,
                32129,
                32130,
                32131,
                32132,
                32135,
                32136,
                32137,
                32138,
                32139,
                32140,
                32141,
                32142,
                32143,
                32145,
                32147,
                32148,
                32149,
                32157,
                32164,
                32168,
                32169,
                32170,
                32173,
                32174,
                32175,
                32176,
                32177,
                32178,
                32180,
                32181,
                32185,
                32187,
                32189,
                32190,
                32193,
                32198,
                32259,
                32260,
                32666,
                32706,
                32713,
                32720,
                32721,
                32722,
                32723,
                32724,
                32725,
                32728,
                32738,
                32739,
                32744,
                32753,
                32759,
                32763,
                32764,
                32774
                );
    }

}
