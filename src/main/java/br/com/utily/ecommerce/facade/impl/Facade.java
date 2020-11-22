package br.com.utily.ecommerce.facade.impl;

import br.com.utily.ecommerce.dao.IDAO;
import br.com.utily.ecommerce.dao.domain.product.IProductDAO;
import br.com.utily.ecommerce.dao.domain.product.category.ICategoryDAO;
import br.com.utily.ecommerce.dao.domain.product.provider.IProviderDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleAddressDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleCreditCardDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleItemDAO;
import br.com.utily.ecommerce.dao.domain.user.customer.IAddressDAO;
import br.com.utily.ecommerce.dao.domain.user.customer.ICreditCardDAO;
import br.com.utily.ecommerce.dao.domain.user.customer.ICustomerDAO;
import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.product.category.Category;
import br.com.utily.ecommerce.entity.domain.product.provider.Provider;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleAddress;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleCreditCard;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItem;
import br.com.utily.ecommerce.entity.domain.user.User;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import br.com.utily.ecommerce.facade.IFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Facade<T extends Entity> implements IFacade<T> {

    private final Map<String, IDAO> daosMap;

    private ICustomerDAO customerDAO;
    private IAddressDAO addressDAO;
    private ICreditCardDAO creditCardDAO;

    private IProductDAO productDAO;
    private ICategoryDAO categoryDAO;
    private IProviderDAO providerDAO;

    private ISaleDAO saleDAO;
    private ISaleItemDAO saleItemDAO;
    private ISaleAddressDAO saleAddressDAO;
    private ISaleCreditCardDAO saleCreditCardDAO;

    public Facade() {
        daosMap = new HashMap<>();
    }

    @Autowired
    private void setCustomerRelatedDAOS(ICustomerDAO customerDAO,
                                        IAddressDAO addressDAO,
                                        ICreditCardDAO creditCardDAO) {
        this.customerDAO = customerDAO;
        this.addressDAO = addressDAO;
        this.creditCardDAO = creditCardDAO;

        initCustomerRelatedDAOSMap();
    }

    @Autowired
    private void setProductRelatedDAOS(IProductDAO productDAO,
                                       ICategoryDAO categoryDAO,
                                       IProviderDAO providerDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.providerDAO = providerDAO;

        initProductRelatedDAOSMap();
    }

    @Autowired
    private void setSaleRelatedDAOS(ISaleDAO saleDAO,
                                    ISaleItemDAO saleItemDAO,
                                    ISaleAddressDAO saleAddressDAO,
                                    ISaleCreditCardDAO saleCreditCardDAO) {
        this.saleDAO = saleDAO;
        this.saleItemDAO = saleItemDAO;
        this.saleAddressDAO = saleAddressDAO;
        this.saleCreditCardDAO = saleCreditCardDAO;

        initSaleRelatedDAOSMap();
    }

    private void initCustomerRelatedDAOSMap() {
        daosMap.put(Customer.class.getName(), customerDAO);
        daosMap.put(Address.class.getName(), addressDAO);
        daosMap.put(CreditCard.class.getName(), creditCardDAO);
    }

    private void initProductRelatedDAOSMap() {
        daosMap.put(Product.class.getName(), productDAO);
        daosMap.put(Category.class.getName(), categoryDAO);
        daosMap.put(Provider.class.getName(), providerDAO);
    }

    private void initSaleRelatedDAOSMap() {
        daosMap.put(Sale.class.getName(), saleDAO);
        daosMap.put(SaleItem.class.getName(), saleItemDAO);
        daosMap.put(SaleAddress.class.getName(), saleAddressDAO);
        daosMap.put(SaleCreditCard.class.getName(), saleCreditCardDAO);
    }

    @Override
    public T save(T entity) {
        T savedEntity = null;
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDAO<T> dao = (IDAO<T>) daosMap.get(entityName);
            savedEntity = dao.save(entity);
        }

        return savedEntity;
    }

    @Override
    public void remove(T entity) {
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            daosMap.get(entityName).delete(entity);
        }
    }

    @Override
    public Optional<T> findById(Long id, T entity) {
        Optional<T> optionalEntity = Optional.empty();
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            optionalEntity = daosMap.get(entityName).findById(id);
        }

        return optionalEntity;
    }

    @Override
    public Optional<T> findBy(Entity targetEntity, T baseEntity) {
        Optional<T> optionalEntity = Optional.empty();
        String entityName = baseEntity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDAO<T> selectedDAO = daosMap.get(entityName);
            optionalEntity = executeFindBy(targetEntity, selectedDAO);
        }

        return optionalEntity;
    }

    @Override
    public List<T> findAll(T entity) {
        List<T> entitiesCollection = Collections.EMPTY_LIST;
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            entitiesCollection = daosMap.get(entityName).findAll();
        }

        return entitiesCollection;
    }

    private Optional<T> executeFindBy(Entity filterEntity, IDAO<T> dao) {
        Optional<T> optionalEntity = Optional.empty();

        if (filterEntity instanceof User) {
            User user = (User) filterEntity;

            if (dao instanceof ICustomerDAO) {
                ICustomerDAO customerDAO = (ICustomerDAO) dao;
                optionalEntity = (Optional<T>) customerDAO.findCustomerByUser(user);
            }
        }

        return optionalEntity;
    }
}
