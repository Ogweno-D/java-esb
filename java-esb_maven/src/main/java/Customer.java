import com.fasterxml.jackson.annotation.JsonProperty;
//
//public class Customer {
//    @JsonProperty("firstName")
//    public String firstName;
//
//    @JsonProperty("lastName")
//    public String lastName;
//
//    @JsonProperty("DOB")
//    public String DOB;
//
//    @JsonProperty("phoneNumber")
//    public String phoneNumber;
//
//    @JsonProperty("email")
//    public String email;
//}
public class Customer {
    // Personal details (existing)
    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("DOB")
    public String DOB;

    @JsonProperty("phoneNumber")
    public String phoneNumber;

    @JsonProperty("email")
    public String email;

    // ISO 20022 Payment-relevant additions:

    // 1. Payment Identification
    @JsonProperty("messageId")
    public String messageId;                // MsgId
    @JsonProperty("creationDateTime")
    public String creationDateTime;         // CreDtTm

    // 2. Payment Information Block
    @JsonProperty("paymentInfoId")
    public String paymentInfoId;            // PmtInfId
    @JsonProperty("paymentMethod")
    public String paymentMethod;            // e.g., "TRF" (transfer)
    @JsonProperty("requestedExecutionDate")
    public String requestedExecutionDate;   // ReqdExctnDt

    // 3. Debtor (payer details)
    @JsonProperty("debtorName")
    public String debtorName;
    @JsonProperty("debtorIBAN")
    public String debtorIBAN;

    // 4. Creditor (payee details)
    @JsonProperty("creditorName")
    public String creditorName;
    @JsonProperty("creditorIBAN")
    public String creditorIBAN;

    // 5. Payment amount
    @JsonProperty("instructedAmount")
    public String instructedAmount;         // numeric value
    @JsonProperty("currency")
    public String currency;                 // currency code, e.g. "EUR"

    // 6. Optional remittance information
    @JsonProperty("remittanceInfo")
    public String remittanceInfo;
}


