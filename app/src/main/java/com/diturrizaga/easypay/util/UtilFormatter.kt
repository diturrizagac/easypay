package com.diturrizaga.easypay.util

import android.annotation.SuppressLint
import java.util.*

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat
import kotlin.math.round


class UtilFormatter {

   companion object {
      fun amountToMoneyFormat(number : Double) : String{
         return String.format("%.2f", number)
      }

      @SuppressLint("SimpleDateFormat")
      fun dateFormatter(date: Long): String {
         val dateFormatted = SimpleDateFormat("dd/MM/yyyy -- HH:mm:ss")
         return dateFormatted.format(Date(date))
      }

      fun Double.round(decimals: Int) : Double {
         var multiplier = 1.0
         repeat(decimals) {
            multiplier *= 10
         }
         return round(this * multiplier) / multiplier
      }
   }


   fun phoneNumberFormatter(phoneString: String?): String {
      var phoneString = phoneString
      if (phoneString != null) {
         phoneString = stripFormat(phoneString)
         val length = phoneString.length
         return if (length == 11) {
            String.format(
               "%s (%s) %s-%s",
               phoneString.substring(0, 1),
               phoneString.substring(1, 4),
               phoneString.substring(4, 7),
               phoneString.substring(7)
            )
         } else if (length == 10) {
            String.format(
               "(%s) %s-%s",
               phoneString.substring(0, 3),
               phoneString.substring(3, 6),
               phoneString.substring(6)
            )
         } else {
            phoneString
         }
      }
      return ""
   }

   fun stripFormat(input: String): String {
      return input.replace("[^0-9]+".toRegex(), "")
   }

   fun zipcodeFormatter(zipcode: String?): String {
      var zipcode = zipcode
      if (zipcode != null) {
         zipcode = stripFormat(zipcode)
         return if (zipcode.length == 9) {
            String.format("%s-%s", zipcode.substring(0, 5), zipcode.substring(5, 9))
         } else {
            zipcode
         }
      }
      return ""
   }

   fun maskAccountNumber(accountNumber: String): String {
      val length = accountNumber.length
      val charArray = CharArray(4)
      Arrays.fill(charArray, '*')
      var last4digits = accountNumber
      if (length >= 4) {
         last4digits = accountNumber.substring(length - 4, length)
      }
      return String.format("%s%s", String(charArray), last4digits)
   }

//   private var currencyFormat: CurrencyFormat? = null
//   private var currencyFormatter: DecimalFormat? = null

   // CONSTRUCTOR


   // GETTERS/SETTERS

   /*fun getCurrencyFormat(): CurrencyFormat? {
       if (currencyFormat == null) {
           val jsonFormat = Configuration.get().getProperty(CurrencyFormat.CURRENCY_FORMAT_KEY)
           if (jsonFormat == null) {
               currencyFormat = CurrencyFormat.DEFAULT_FORMAT
           } else {
               currencyFormat = Platform.get().getGson().fromJson(jsonFormat, CurrencyFormat::class.java)
           }
       }
       return currencyFormat
   }

   fun getCurrencyFormatter(): DecimalFormat {
       if (currencyFormatter == null) {
           val format = getCurrencyFormat()

           val decimalFormatSymbols = DecimalFormatSymbols()
           decimalFormatSymbols.setGroupingSeparator(format!!.getGroupingSeparator().charAt(0))
           decimalFormatSymbols.setDecimalSeparator(format!!.getDecimalSeparator().charAt(0))

           currencyFormatter = DecimalFormat()
           currencyFormatter!!.setGroupingSize(format!!.getGroupSize())
           currencyFormatter!!.setDecimalSeparatorAlwaysShown(true)
           currencyFormatter!!.setMaximumFractionDigits(format!!.getPrecision())
           currencyFormatter!!.setMinimumFractionDigits(format!!.getDecimalMinimumSize())
           currencyFormatter!!.setMinimumIntegerDigits(format!!.getIntegerMinimumSize())
           currencyFormatter!!.setDecimalFormatSymbols(decimalFormatSymbols)
       }
       return currencyFormatter
   }*/

   /**
    * Reset any cached/derived formatters on the utility.
    *
    *
    * Generally only used for testing purposes
    */
//   fun reset() {
//      currencyFormat = null
//      currencyFormatter = null
//   }

   // UTILITY
//    fun asRawAmount(number: Double?): String {
//        var number: Double? = number ?: return ""
//        val decimalPrecision = Math.pow(10.0, getCurrencyFormat()!!.getPrecision()).toInt()
//        number = number!! * decimalPrecision
//        return Math.round(number).toString()
//    }
//
//    fun getStringAmount(number: Double?): String {
//        var number = number
//        if (number == null) {
//            number = 0.0
//        }
//        return getCurrencyFormatter().format(number)
//    }
//
//    fun getString(number: Double?): String {
//        var number = number
//        if (number == null) {
//            number = 0.0
//        }
//
//        val tempString = getCurrencyFormatter().format(number)
//
//        return if (getCurrencyFormat()!!.getCurrencyPosition() === CurrencyFormat.Position.prefix) {
//            String.format("%s%s", getCurrencyFormat()!!.getCurrencySymbol(), tempString)
//        } else {
//            String.format("%s%s", tempString, getCurrencyFormat()!!.getCurrencySymbol())
//        }
//    }
//
//    fun getDecimal(formattedNumber: String): Double? {
//        var formattedNumber = formattedNumber
//        val symbolLength = getCurrencyFormat()!!.getCurrencySymbol().length()
//        try {
//            if (getCurrencyFormat()!!.getCurrencyPosition() === CurrencyFormat.Position.prefix) {
//                formattedNumber = formattedNumber.substring(symbolLength)
//            } else {
//                formattedNumber = formattedNumber.substring(0, formattedNumber.length - symbolLength)
//            }
//
//            return getCurrencyFormatter().parse(formattedNumber).doubleValue()
//        } catch (e: ParseException) {
//            return null
//        }
//
//    }
//
//    fun getDepositLimitFormat(amount: Any?): String {
//        val decimalFormat = DecimalFormat("0.00")
//        return if (amount != null) {
//            if (amount is String) {
//                String.format(
//                    "%s%s",
//                    getCurrencyFormat()!!.getCurrencySymbol(),
//                    decimalFormat.format(java.lang.Double.valueOf((amount as String?)!!))
//                )
//            } else {
//                String.format("%s%s", getCurrencyFormat()!!.getCurrencySymbol(), decimalFormat.format(amount))
//            }
//        } else {
//            ""
//        }
//    }
}