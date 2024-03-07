CREATE OR REPLACE FORCE VIEW ranking AS
SELECT
    gm.user_id,
    gm.pool_size,
    gm.length,
    COUNT(*) AS game_count,
    AVG(agg.guess_count) AS avg_guess_count,
    AVG(agg.duration) AS avg_duration
FROM
    game AS gm
        JOIN (
        SELECT
            gm.game_id,
            COUNT(*) AS guess_count,
            (MAX(gs.created) - MIN(gs.created)) AS duration,
            MAX(gs.correct) AS correct
        FROM
            game AS gm
                JOIN guess AS gs
                     ON gs.game_id = gm.game_id
        GROUP BY
            gm.game_id
    ) AS agg
             ON agg.game_id = gm.game_id
WHERE
    agg.correct = gm.length
GROUP BY
    gm.user_id,
    gm.pool_size,
    gm.length;
