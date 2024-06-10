-- sale 테이블 불일치 데이터 개수 확인
SELECT COUNT(*) AS sale_mismatch_count
FROM (SELECT re.id
      FROM real_estate re
               JOIN sale s ON re.id = s.id
      WHERE re.real_estate_type = 'APT_TRADE'
        AND (
                  re.cancel_deal_day != s.cancel_deal_day
              OR re.cancel_deal_type != s.cancel_deal_type
              OR re.agent_address != s.agent_address
              OR re.deal_amount != s.deal_amount
              OR re.deal_type != s.deal_type
          )) AS sale_mismatches;

-- rent 테이블 불일치 데이터 개수 확인
SELECT COUNT(*) AS rent_mismatch_count
FROM (SELECT re.id
      FROM real_estate re
               JOIN rent r ON re.id = r.id
      WHERE re.real_estate_type IN ('APT_RENT', 'OFFICETEL_RENT', 'DETACHEDHOUSE_RENT', 'ROWHOUSE_RENT')
        AND (
                  re.request_renewal_right != r.request_renewal_right
              OR re.contract_type != r.contract_type
              OR re.contract_period != r.contract_period
              OR re.deposit != r.deposit
              OR re.deposit_before != r.deposit_before
              OR re.monthly_rent != r.monthly_rent
              OR re.monthly_rent_before != r.monthly_rent_before
              OR re.si_gun_gu != r.si_gun_gu
          )) AS rent_mismatches;