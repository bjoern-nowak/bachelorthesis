from contextlib import nullcontext

import pytest
from parameterized import parameterized

from dsaheldenbogen.app.engine.engine import Engine
from dsaheldenbogen.app.engine.exceptions import HeroInvalidError
from dsaheldenbogen.app.models.hero import Hero
from dsaheldenbogen.app.models.rulebook import Rulebook


class TestHeroValidation:

    @parameterized.expand([
        (0, 'Mensch', 'Aranier'),
        (1, 'Mensch', ''),
        (1, 'Mensch', '_invalid_'),
        (1, 'Mensch', 'Erzzwerge'),
    ])
    def test_culture_usable(self, error_count: int, race: str, culture: str):
        # given:
        engine = Engine(Rulebook.map(['dsa5']))
        hero = Hero(name="name",
                    experience_level='Durchschnittlich',
                    race=race,
                    culture=culture,
                    profession='Händler',
                    talents=[],
                    combat_techniques=[],
                    advantages=[],
                    disadvantages=[],
                    )
        # when:
        with pytest.raises(HeroInvalidError) if error_count > 0 else nullcontext() as ctx:
            engine.validate(hero)
        # then:
        if ctx and ctx.value:
            assert error_count == len(ctx.value.errors), ctx.value.errors

    @parameterized.expand([
        (0, 'Mensch', 'Menschlichekultur', 'Händler'),
        (1, 'Mensch', 'Menschlichekultur', ''),
        (1, 'Mensch', 'Menschlichekultur', '_invalid_'),
        (1, 'Mensch', 'Menschlichekultur', 'Zauberweber'),
    ])
    def test_profession_usable(self, error_count: int, race: str, culture: str, profession: str):
        # given:
        engine = Engine(Rulebook.map(['dsa5']))
        hero = Hero(name="name",
                    experience_level='Durchschnittlich',
                    race=race,
                    culture=culture,
                    profession=profession,
                    talents=[],
                    combat_techniques=[],
                    advantages=[],
                    disadvantages=[],
                    )
        # when:
        with pytest.raises(HeroInvalidError) if error_count > 0 else nullcontext() as ctx:
            engine.validate(hero)
        # then:
        if ctx and ctx.value:
            assert error_count == len(ctx.value.errors), ctx.value.errors