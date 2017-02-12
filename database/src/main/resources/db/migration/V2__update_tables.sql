Update cities.country
    set
        co_icon_flag = 'http://flagpedia.net/data/flags/mini/de.png',
        co_small_flag = 'http://flagpedia.net/data/flags/small/de.png',
        co_big_flag = 'http://flagpedia.net/data/flags/normal/de.png',
        co_population = 80619000
    where co_name like 'GERMANY';

Update cities.country
    set
        co_icon_flag = 'http://flagpedia.net/data/flags/mini/tr.png',
        co_small_flag = 'http://flagpedia.net/data/flags/small/tr.png',
        co_big_flag = 'http://flagpedia.net/data/flags/normal/tr.png',
        co_population = 76667864
    where co_name like 'TURKEY';

Update cities.city
    set
        ci_latitude = 39.933363,
        ci_longitude = 32.859742,
        ci_wiki_url = 'https://en.wikipedia.org/wiki/Ankara'
    where ci_name like 'ANKARA';

Update cities.city
    set
        ci_latitude = 41.008238,
        ci_longitude = 28.978359,
        ci_wiki_url = 'https://en.wikipedia.org/wiki/Istanbul'
    where ci_name like 'ISTANBUL';

Update cities.city
    set
        ci_latitude = 52.520007,
        ci_longitude = 13.404954,
        ci_wiki_url = 'https://en.wikipedia.org/wiki/Berlin'
    where ci_name like 'BERLIN';

Update cities.city
    set
        ci_latitude = 50.737430,
        ci_longitude = 7.098207,
        ci_wiki_url = 'https://en.wikipedia.org/wiki/Bonn'
    where ci_name like 'BONN';