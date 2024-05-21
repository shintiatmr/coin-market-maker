package com.ajaib.coin.market.maker.type;

import java.util.HashMap;
import java.util.Map;
import lombok.Setter;

public enum ErrorType {

  /* GENERICS */
  APPROVED_OK("EC0000000", "APPROVED/OK"),
  REST_CONTROLLER_ERROR("EC0000996", "Invalid Request"),
  INVALID_HEADER_PARAM("EC0000997", "Permintaan tidak dapat dipenuhi. Silahkan coba lagi"),
  INVALID_BODY_PARAM("EC0000999", "Permintaan tidak sesuai. Silahkan coba lagi"),
  ERROR_CONNECTIONS("EC0009888", "Koneksi bermasalah. Silakan coba lagi nanti"),
  INTERNAL_SERVER_ERROR("EC0009999", "Ada yang salah, silakan coba lagi"),
  /* GENERICS */

  /* AUTH PLATFORM */
  EXPIRED_TOKEN("EC0000005", "Sesi anda telah berakhir"),
  INVALID_TOKEN("EC0000006", "Sesi anda tidak ditemukan"),
  /* AUTH PLATFORM */

  /* COIN PLATFORM */
  INVALID_CUSTOMER_ERROR("EC0008000", "Coin Custody: invalid customer"),

  COIN_MARKET_MAKER_MUTEX_ERROR("EC0009100", "this transaction still locked, please try it again"),
  COIN_MARKET_MAKER_SYSTEM_PARAMETER_NOT_FOUND("EC0009101", "system parameter not found"),
  /* COIN PLATFORM */

  /* STOCK PLATFORM */
  CUSTOMER_STATUS_BUY_VALIDATED("EC0000001", "CUSTOMER SUSPEND BUY"),
  CUSTOMER_STATUS_SELL_VALIDATED("EC0000002", "CUSTOMER SUSPEND SELL"),
  PRICE_LIMIT("EC0000003", "CHECK PRICE LIMIT"),
  AMEND_INVALID("EC0000004",
      "Tidak dapat melakukan Amend. Periksa Buying Power/harga pada Order Book. Jika sudah memasuki jam penutupan pasar, silakan Amend ke harga penutupan"),
  STATUS_FAIL_FGS("EC0000998", "FGS return failed"),
  WRONG_AUTHENTICATION("EC0009000", "Email atau password salah. Silahkan periksa kembali."),
  USER_NOT_REGISTERED("EC0009001", "User not registered in stock"),
  STATUS_FLOW_INVALID("EC0009002", "Status flow invalid / already process"),
  USER_KYC_INACTIVE("EC0009003", "User KYC status is inactive"),
  LOCK_TRANSACTION("EC0009005", "Transaksi lain sedang berlangsung, mohon coba beberapa saat lagi"),
  BANK_NOT_MATCH("EC0009006", "Wrong Bank Reff Code Process"),
  DATA_INVALID("EC0009007", "Data Invalid"),
  STATUS_FAIL_AJAIB_STOCK("EC1000001", "Ajaib Stock return failed"),
  /* STOCK PLATFORM */

  /* PAYMENTS PLATFORM */
  STATUS_FAIL_PERMATA("EC0100001",
      "Bank sedang melakukan pemindahan dan penyesuaian data. Silakan coba lagi nanti.");
  /* PAYMENTS PLATFORM */

  private static Map<String, ErrorType> valueToTextMapping;
  private String value;
  @Setter
  private String description;

  ErrorType(String value, String description) {
    this.value = value;
    this.description = description;
  }

  private static void initMapping() {
    valueToTextMapping = new HashMap<>();
    for (ErrorType s : values()) {
      valueToTextMapping.put(s.value, s);
    }
  }

  public static ErrorType getStatusCode(String i) {
    if (valueToTextMapping == null) {
      initMapping();
    }
    return valueToTextMapping.get(i);
  }

  public String getDescription() {
    return description;
  }

  public String getValue() {
    return value;
  }
}
