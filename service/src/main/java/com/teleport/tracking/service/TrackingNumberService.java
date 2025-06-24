package com.teleport.tracking.service;

import com.teleport.tracking.dto.TrackingNumberResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TrackingNumberService {
    private static final SecureRandom random = new SecureRandom();

    /**
     * how this works :
     * 1. generate n digit prefix (check further the implementation) -> this is used to handle uniqueness in each timestamp
     * 2. concat with created_at
     * 3. use base36 to transform from concatenated digit to A-Z0-9
     *
     * @param origin       will be used to generate random prefix digit
     * @param destination  will be used to generate random prefix digit
     * @param weight       will be used to generate random prefix digit
     * @param created_at   will be used as main logic
     * @param customerId   will be used to generate random prefix digit
     * @param customerName will be used to generate random prefix digit
     * @param customerSlug will be used to generate random prefix digit
     * @return tracking number.
     */
    public TrackingNumberResponse generateTrackingNumber(
            String origin,
            String destination,
            BigDecimal weight,
            OffsetDateTime created_at,
            UUID customerId,
            String customerName,
            String customerSlug
    ) {
        try {
            long createdAtMillis = created_at.toInstant().toEpochMilli();
            int prefixDigit = getPrefixRandomDigit(origin, destination, weight, customerId, customerName, customerSlug);
            BigInteger concatResult = concatPrefixAndCreatedAtMillis(createdAtMillis, prefixDigit);
            return new TrackingNumberResponse(concatResult.toString(36).toUpperCase(), OffsetDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate tracking number", e);
        }
    }

    private static BigInteger concatPrefixAndCreatedAtMillis(long createdAtMillis, int prefixDigit) {
        long multiplierForPrefixDigit = (long) Math.pow(10, (int) Math.log10(createdAtMillis) + 1);
        return BigInteger.valueOf(prefixDigit)
                .multiply(BigInteger.valueOf(multiplierForPrefixDigit))
                .add(BigInteger.valueOf(createdAtMillis));
    }

    /**
     * prefix will have 7 digit. (1000000 - 9999999)
     * first digit will be random from 1 - 9 (will never be 0, if 0 then it is possible not to have 6 digit prefix)
     * second and third digit will be based on customer input and will be randomized as well.
     * 4th - 7th digit will be random from 0000 - 9999
     */
    private static int getPrefixRandomDigit(String origin,
                                            String destination,
                                            BigDecimal weight,
                                            UUID customerId,
                                            String customerName,
                                            String customerSlug) {
        int prefixRandom; //will have 6 digit

        // #1 first digit
        prefixRandom = 1 + random.nextInt(9) * 1000000; //1000000, 2000000, 3000000, ..., 9000000

        // #2 second and third digit will be coming from all parameters Input.
        // will sum 3 random character from allParametersInput and mod 100 to get 2 random digit
        String allParametersInput = customerName + origin + destination + weight + customerId + customerSlug;
        int randomIndexMax = Math.min(150, allParametersInput.length()); //max index 149

        int sum = 0;
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(randomIndexMax);
            sum += allParametersInput.charAt(randomIndex);
        }
        prefixRandom = prefixRandom + sum % 100 * 10000; // #1 will be added by 0, 10000, 20000, ..., 100000, 110000, ..., 990000

        // #3 forth until seventh digit will be random
        prefixRandom = prefixRandom + random.nextInt(10000); // #2 will be added by 0 - 9999

        return prefixRandom;
    }
}
