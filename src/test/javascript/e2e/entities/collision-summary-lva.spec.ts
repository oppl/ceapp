import { browser, element, by, $ } from 'protractor';

describe('CollisionSummaryLva e2e test', () => {

    const username = element(by.id('username'));
    const password = element(by.id('password'));
    const entityMenu = element(by.id('entity-menu'));
    const accountMenu = element(by.id('account-menu'));
    const login = element(by.id('login'));
    const logout = element(by.id('logout'));

    beforeAll(() => {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
        browser.waitForAngular();
    });

    it('should load CollisionSummaryLvas', () => {
        entityMenu.click();
        element.all(by.css('[routerLink="collision-summary-lva"]')).first().click().then(() => {
            const expectVal = /ceappApp.collisionSummaryLva.home.title/;
            element.all(by.css('h2 span')).first().getAttribute('jhiTranslate').then((value) => {
                expect(value).toMatch(expectVal);
            });
        });
    });

    it('should load create CollisionSummaryLva dialog', () => {
        element(by.css('button.create-collision-summary-lva')).click().then(() => {
            const expectVal = /ceappApp.collisionSummaryLva.home.createOrEditLabel/;
            element.all(by.css('h4.modal-title')).first().getAttribute('jhiTranslate').then((value) => {
                expect(value).toMatch(expectVal);
            });

            element(by.css('button.close')).click();
        });
    });

    afterAll(() => {
        accountMenu.click();
        logout.click();
    });
});
