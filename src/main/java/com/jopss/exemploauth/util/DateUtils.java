package com.jopss.exemploauth.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

public final class DateUtils {

	public static final int SEGUNDO = 1;
	public static final int MINUTO = 2;
	public static final int HORA = 3;
	public static final int DIA = 4;
	public static final int SEMANA = 5;
	public static final int MES = 6;
	public static final int ANO = 7;

	private static final DateFormat DF_DATA = new SimpleDateFormat("dd/MM/yyyy");

	public static final DateFormat DF_DATA_HORA_MINUTO = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");

	/**
	 * Arredonta a data desconsiderando a hora, minutos, segundos e milisegundos. Exemplo: 14/10/2012 10:11:12 -> 14/10/2012 00:00:00.
	 * 
	 * @param data {@link Date}
	 * @return {@link Date}
	 */
	public static Date arredondaDataZerandoHora(Date data) {
		if (data == null) {
			return null;
		}
		return org.apache.commons.lang.time.DateUtils.truncate(data, Calendar.DAY_OF_MONTH);
	}

	/**
	 * Arredonta a data desconsiderando a hora, minutos, segundos e milisegundos. Exemplo: 14/10/2012 10:11:12 -> 14/10/2012 23:59:59.
	 * 
	 * @param data {@link Date}
	 * @return {@link Date}
	 */
	public static Date arredondaDataComMaximaHora(Date data) {
		if (data == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 23:59:59");
		DateFormat dateFormatTransf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			return dateFormatTransf.parse(dateFormat.format(data));
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * Retorna a diferenca em dias entre duas datas, desconsiderando horas, minutos e unidades menores
	 * <p>
	 * Caso a data inicial for maior que a data final a diferenca sera negativa
	 * </p>
	 * 
	 * @param dataInicial Data Inicial
	 * @param dataFinal Data Final
	 * @return Quantidade de dias entre as duas datas
	 */
	public static int getDiferencaDias(Date dataInicial, Date dataFinal) {
		DateTime dateTimeInicial = new DateTime(dataInicial);
		DateTime dateTimeFinal = new DateTime(dataFinal);
		return Days.daysBetween(dateTimeInicial.withTimeAtStartOfDay(), dateTimeFinal.withTimeAtStartOfDay()).getDays();
	}

	/**
	 * Retorna a diferenca em horas entre duas datas
	 * <p>
	 * Caso a data inicial for maior que a data final a diferenca sera negativa
	 * </p>
	 * 
	 * @param dataInicial Data Inicial
	 * @param dataFinal Data Final
	 * @return Quantidade de horas entre as duas datas
	 */
	public static int getDiferencaHoras(Date dataInicial, Date dataFinal) {
		DateTime periodoInicial = new DateTime(dataInicial);
		DateTime periodoFinal = new DateTime(dataFinal);

		return Hours.hoursBetween(periodoInicial, periodoFinal).getHours();
	}

	public static int getDiferencaAnos(Date dataInicial, Date dataFinal) {
		DateTime periodoInicial = new DateTime(dataInicial);
		DateTime periodoFinal = new DateTime(dataFinal);

		return Years.yearsBetween(periodoInicial, periodoFinal).getYears();
	}

	public static int getDiferencaMeses(Date dataInicial, Date dataFinal) {
		DateTime periodoInicial = new DateTime(dataInicial);
		DateTime periodoFinal = new DateTime(dataFinal);

		return Months.monthsBetween(periodoInicial, periodoFinal).getMonths();
	}

	/**
	 * @param data
	 * @return {@link String} em formato ptBR
	 */
	public static String FormatData_ptBR(Date data) {
		return DF_DATA.format(data);
	}

	/**
	 * @param data
	 * @return {@link String} em formato ptBR com hora
	 */
	public static String FormatDataHora_ptBR(Date data) {
		return DF_DATA_HORA_MINUTO.format(data);
	}

	/**
	 * Format uma data de acordo com o padrao passado
	 * 
	 * @param data
	 * @param pattern
	 * @return String
	 * @see {@link SimpleDateFormat}
	 */
	public static String FormatData_Pattern(Date data, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(data);
	}

	/**
	 * Soma a uma data (dataInicio) um tempo especificado por valor e unidade .Ex: Date dataAtualizada = DateUtilsTribunais.somaData(new Date(), 2, DateUtilsTribunais.MES); <br/>
	 * Pode-se subtrair uma data, passando o valor negativo.
	 * 
	 * @param dataInicio
	 * @param valor
	 * @param unidade
	 * @return {@link Date}
	 */
	public static Date somaSubtraiData(Date data, int valor, int unidade) {
		Date dataReturn = (Date) data.clone();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dataReturn);

		if (unidade == DateUtils.SEGUNDO) {
			calendar.add(Calendar.SECOND, valor);
		} else if (unidade == DateUtils.MINUTO) {
			calendar.add(Calendar.MINUTE, valor);
		} else if (unidade == DateUtils.HORA) {
			calendar.add(Calendar.HOUR_OF_DAY, valor);
		} else if (unidade == DateUtils.DIA) {
			calendar.add(Calendar.DAY_OF_YEAR, valor);
		} else if (unidade == DateUtils.SEMANA) {
			calendar.add(Calendar.WEEK_OF_YEAR, valor);
		} else if (unidade == DateUtils.MES) {
			calendar.add(Calendar.MONTH, valor);
		} else if (unidade == DateUtils.ANO) {
			calendar.add(Calendar.YEAR, valor);
		}

		dataReturn.setTime(calendar.getTimeInMillis());
		return dataReturn;
	}

	/**
	 * Adiciona uma quantidade de dias a uma data. Se o valor for negativo, será feito uma subtração de datas.
	 * 
	 * @param data
	 * @param quantidade
	 * @return
	 */
	public static Date adicionarDias(Date data, int quantidade) {
		DateTime dataInicial = new DateTime(data);
		return dataInicial.plusDays(quantidade).toDate();
	}

	/**
	 * Verifica se a data1 é maior que a data2, considerando apenas a porção do dia das duas datas.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean isDataMaior(Date data1, Date data2) {
		return DateTimeComparator.getDateOnlyInstance().compare(data1, data2) > 0;
	}

	/**
	 * Verifica se a data1 é menor que a data2, considerando apenas a porção do dia das duas datas.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean isDataMenor(Date data1, Date data2) {
		return DateTimeComparator.getDateOnlyInstance().compare(data1, data2) < 0;
	}

	/**
	 * Verifica se a data1 é menor ou igual que a data2, considerando apenas a porção do dia das duas datas.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean isDataMenorOuIgual(Date data1, Date data2) {
		return DateTimeComparator.getDateOnlyInstance().compare(data1, data2) <= 0;
	}

	/**
	 * Verifica se a data1 é igual a data2, considerando apenas a porção do dia das duas datas.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean isDataIgual(Date data1, Date data2) {
		return DateTimeComparator.getDateOnlyInstance().compare(data1, data2) == 0;
	}

	/**
	 * Retorna um Date atraves de uma String no formado dd/MM/yyyy
	 * 
	 * @param data objeto String com a data
	 * @return Date
	 */
	public static Date retornaData(String data) {
		try {
			return DF_DATA.parse(data);
		} catch (ParseException ex) {
			return null;
		}
	}

	public static Date retornaData(String data, DateFormat df) {
		try {
			return df.parse(data);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * Com a data e a unidade especifica, retorna um inteiro representando somente o DIA, MES ou ANO.
	 * 
	 * @param data Date referente
	 * @param unidade atributos estaticos desta classe, sendo poss�vel DIA, MES ou ANO.
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Integer retornaUnidade(Date data, int unidade) throws IllegalArgumentException {
		Calendar cale = GregorianCalendar.getInstance();
		cale.setTime(data);

		if (unidade == DateUtils.DIA) {
			return cale.get(Calendar.DAY_OF_MONTH);
		} else if (unidade == DateUtils.MES) {
			return cale.get(Calendar.MONTH) + 1;
		} else if (unidade == DateUtils.ANO) {
			return cale.get(Calendar.YEAR);
		} else if (unidade == DateUtils.HORA) {
			return cale.get(Calendar.HOUR_OF_DAY);
		} else if (unidade == DateUtils.MINUTO) {
			return cale.get(Calendar.MINUTE);
		} else if (unidade == DateUtils.SEGUNDO) {
			return cale.get(Calendar.SECOND);
		} else if (unidade == DateUtils.SEMANA) {
			return cale.get(Calendar.WEEK_OF_MONTH);
		} else {
			throw new IllegalArgumentException("Unidade especificada nao valida. Escolha: DIA, MES ou ANO");
		}

	}

	public static String retornarHora(Date date) {
		DateFormat dateFormatHora = new SimpleDateFormat("HH:mm:ss");
		return dateFormatHora.format(date);
	}

	/**
	 * @return true se a data inicial é menor que a data final, false caso contrario. Se alguma data informada for nula, retorna false.
	 */
	public static boolean isDataInicialFinalValidas(Date dataInicial, Date dataFinal) {
		if (dataInicial != null && dataFinal != null) {
			return DateUtils.isDataMenorOuIgual(dataInicial, dataFinal);
		}

		return false;
	}

	/**
	 * Retorna lista com os proximos anos a partir do atual.
	 * 
	 * @param numero de proximos anos a serem retornados.
	 * @return List<String>
	 */
	public static List<String> retornarListaProximosAnos(int qtdProximosAnos) {

		LocalDate dataAtual = new LocalDate();
		LocalDate dataFinal = dataAtual.withFieldAdded(DurationFieldType.years(), qtdProximosAnos);

		List<String> retorno = new ArrayList<String>();
		int anos = Years.yearsBetween(dataAtual, dataFinal).getYears();
		for (int i = 0; i < anos; i++) {
			retorno.add("" + dataAtual.withFieldAdded(DurationFieldType.years(), i).getYear());
		}

		Collections.sort(retorno);

		return retorno;
	}
        
        public static LocalDate[] retornarMaximoEMinimoDiaDoMes(final int mes, int diaInit) {
                LocalDate[] arr = new LocalDate[2];
                int ano = new DateTime().year().get();
                if ((mes >= 1) && (mes <= 12)) {
                        LocalDate dataInicial = new LocalDate(ano, mes, diaInit);
                        LocalDate dataFinal = new LocalDate(ano, mes, dataInicial.dayOfMonth().getMaximumValue());
                        arr[0] = dataInicial;
                        arr[1] = dataFinal;
                }
                return arr;
        }
        
        public static LocalDate[] retornarMaximoEMinimoDiaDoMes(final int mes) {
                return DateUtils.retornarMaximoEMinimoDiaDoMes(mes, 1);
        }
        
        public static List<LocalDate[]> retornarMaximoEMinimoUltimosMesesAPartirHoje(int qtdUltimosAnos) {
                List<LocalDate[]> lista = new ArrayList<>();
                LocalDate dataAtual = new LocalDate();

                for(int x = 1; x <= qtdUltimosAnos; x++){
                        int mes = dataAtual.monthOfYear().get();
                        LocalDate[] minMax = DateUtils.retornarMaximoEMinimoDiaDoMes(mes);
                        lista.add(minMax);
                        dataAtual = dataAtual.minusMonths(1);
                }
                
                return lista;
	}
        
        public static List<LocalDate[]> retornarMaximoEMinimoPorMesReferenciaAteHoje(Date dataRefParam) {
                List<LocalDate[]> lista = new ArrayList<>();
                LocalDate dataRef = new LocalDate(dataRefParam);
                LocalDate dataHoje = new LocalDate();
                int mesesDif = Months.monthsBetween(dataRef, dataHoje).getMonths();
                if(mesesDif == 0){
                        mesesDif = 1;
                }
                
                for(int x = 1; x <= mesesDif; x++){
                        int mes = dataRef.monthOfYear().get();
                        int dia = dataRef.dayOfMonth().get();
                        LocalDate[] minMax = DateUtils.retornarMaximoEMinimoDiaDoMes(mes, dia);
                        lista.add(minMax);
                        dataRef = dataRef.plusMonths(1);
                }
                return lista;
	}

	public static int retornarAnoCorrente() {
		return retornaUnidade(new Date(), DateUtils.ANO);
	}

	/**
	 * Retorna lista com os nomes de todos os meses.
	 * 
	 * OBS: nesta versao atual, no retorno de getMonths, o ultimo item sempre era vazio. Por conta disso pega-se todos os dados do array menos o ultimo.
	 * 
	 * @return List<String>
	 */
	public static List<String> retornarListaMesesExtenso() {
		return Arrays.asList(new DateFormatSymbols().getMonths()).subList(0, 12);
	}

	public static boolean isTempoValido(String tempo) {
		boolean resposta = true;
		if (StringUtils.isBlank(tempo)) {
			resposta = false;
		} else if (tempo.length() < 8) {
			resposta = false;
		} else {
			int hora = Integer.valueOf(tempo.substring(0, 2));
			int minuto = Integer.valueOf(tempo.substring(3, 5));
			int segundo = Integer.valueOf(tempo.substring(6, 8));
			if (hora > 23) {
				resposta = false;
			} else if (minuto > 59) {
				resposta = false;
			} else if (segundo > 59) {
				resposta = false;
			}
		}
		return resposta;
	}

	public static Date adicionarTempo(Date data, String tempo) {
		if (data != null && isTempoValido(tempo)) {
			int hora = Integer.valueOf(tempo.substring(0, 2));
			int minuto = Integer.valueOf(tempo.substring(3, 5));
			int segundo = Integer.valueOf(tempo.substring(6, 8));

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data);
			calendar.set(Calendar.HOUR_OF_DAY, hora);
			calendar.set(Calendar.MINUTE, minuto);
			calendar.set(Calendar.SECOND, segundo);
			return calendar.getTime();
		}
		return data;
	}

	/**
	 * Verifica se a data Inicial é maior do que a data final. Verifica o tempo das datas passado como argumento.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return boolean
	 */
	public static boolean isTempoMenorOuIgual(Date dataInicial, Date dataFinal) {
		return DateTimeComparator.getInstance().compare(dataInicial, dataFinal) <= 0;
	}

        public static Date getDataDiaAnteriorUltimoMinuto() {
		Date date = adicionarDias(new Date(), -1);

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 59);
		return c.getTime();
	}
        
        public static List<LocalDate> getDiasDeUmMes(int mes, int ano){
                List<LocalDate> dias = new ArrayList<>();
                
                LocalDate day = new LocalDate().withMonthOfYear(mes).withYear(ano).withDayOfMonth(1); //primeiro dia do mes
                LocalDate nextMonthFirstDay = day.plusMonths(1); //proximo mes.
                while (day.isBefore(nextMonthFirstDay)) {
                    dias.add(day);
                    day = day.plusDays(1);
                }
                return dias;
        }

}
