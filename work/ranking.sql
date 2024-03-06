CREATE VIEW ranking AS
SELECT
    gm.USER_ID,
    gm.POOL_SIZE,
    gm.LENGTH,
    COUNT(*) AS game_count,
    AVG(agg.guess_count) AS avg_guess_count,
    AVG(agg.duration) AS avg_duration
FROM
    GAME AS gm
        JOIN (
        SELECT
            gm.GAME_ID,
            COUNT(*)                            AS guess_count,
            (MAX(gs.CREATED) - MIN(gs.CREATED)) AS duration,
            MAX(gs.CORRECT)                     AS correct
        FROM
            GAME AS gm
                JOIN GUESS AS gs
                    ON gs.GAME_ID = gm.GAME_ID
        GROUP BY
            gm.GAME_ID
    ) AS agg
             ON agg.GAME_ID = gm.GAME_ID
WHERE
    agg.correct = gm.LENGTH
GROUP BY
    gm.USER_ID,
    gm.POOL_SIZE,
    gm.LENGTH;