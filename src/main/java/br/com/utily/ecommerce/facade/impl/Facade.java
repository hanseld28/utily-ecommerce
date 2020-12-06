package br.com.utily.ecommerce.facade.impl;

import br.com.utily.ecommerce.dao.IDAO;
import br.com.utily.ecommerce.dao.IDomainDAO;
import br.com.utily.ecommerce.dao.domain.IDateFilter;
import br.com.utily.ecommerce.dao.domain.product.IProductDAO;
import br.com.utily.ecommerce.dao.domain.product.category.ICategoryDAO;
import br.com.utily.ecommerce.dao.domain.product.provider.IProviderDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleAddressDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleCreditCardDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleDAO;
import br.com.utily.ecommerce.dao.domain.sale.ISaleItemDAO;
import br.com.utily.ecommerce.dao.domain.stock.IStockDAO;
import br.com.utily.ecommerce.dao.domain.stock.IStockHistoryDAO;
import br.com.utily.ecommerce.dao.domain.user.customer.IAddressDAO;
import br.com.utily.ecommerce.dao.domain.user.customer.ICreditCardDAO;
import br.com.utily.ecommerce.dao.domain.user.customer.ICustomerDAO;
import br.com.utily.ecommerce.dao.domain.voucher.ICustomerVoucherDAO;
import br.com.utily.ecommerce.dao.domain.voucher.IVoucherDAO;
import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.product.category.Category;
import br.com.utily.ecommerce.entity.domain.product.provider.Provider;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleAddress;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleCreditCard;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItem;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.stock.Stock;
import br.com.utily.ecommerce.entity.domain.stock.StockHistory;
import br.com.utily.ecommerce.entity.domain.user.User;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
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

    private IStockDAO stockDAO;
    private IStockHistoryDAO stockHistoryDAO;

    private IVoucherDAO voucherDAO;
    private ICustomerVoucherDAO customerVoucherDAO;

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
    private void setStockRelatedDAOS(IStockDAO stockDAO,
                                     IStockHistoryDAO stockHistoryDAO) {
        this.stockDAO = stockDAO;
        this.stockHistoryDAO = stockHistoryDAO;

        initStockRelatedDAOSMap();
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

    @Autowired
    private void setVoucherRelatedDAOS(IVoucherDAO voucherDAO,
                                       ICustomerVoucherDAO customerVoucherDAO) {
        this.voucherDAO = voucherDAO;
        this.customerVoucherDAO = customerVoucherDAO;

        initVoucherRelatedDAOSMap();
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

    private void initStockRelatedDAOSMap() {
        daosMap.put(Stock.class.getName(), stockDAO);
        daosMap.put(StockHistory.class.getName(), stockHistoryDAO);
    }

    private void initSaleRelatedDAOSMap() {
        daosMap.put(Sale.class.getName(), saleDAO);
        daosMap.put(SaleItem.class.getName(), saleItemDAO);
        daosMap.put(SaleAddress.class.getName(), saleAddressDAO);
        daosMap.put(SaleCreditCard.class.getName(), saleCreditCardDAO);
    }

    private void initVoucherRelatedDAOSMap() {
        daosMap.put(Voucher.class.getName(), voucherDAO);
        daosMap.put(CustomerVoucher.class.getName(), customerVoucherDAO);
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
    public T saveAndFlush(T entity) {
        T savedEntity = null;
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDAO<T> dao = (IDAO<T>) daosMap.get(entityName);
            savedEntity = dao.saveAndFlush(entity);
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
            IDAO<T> selectedDAO = daosMap.get(entityName);

            if (selectedDAO instanceof IDateFilter) {
                entitiesCollection = (List<T>) ((IDateFilter) selectedDAO).findAllByOrderByDateDesc();
            } else {
                entitiesCollection = selectedDAO.findAll();
            }
        }

        return entitiesCollection;
    }

    @Override
    public List<T> findAllBy(Entity targetEntity, T baseEntity) {
        List<T> entitiesCollection = Collections.EMPTY_LIST;
        String entityName = baseEntity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDAO<T> selectedDAO = daosMap.get(entityName);
            entitiesCollection = executeFindAllBy(targetEntity, selectedDAO);
        }

        return entitiesCollection;
    }

    @Override
    public Optional<T> findActivatedById(Long id, Entity entity) {
        Optional<T> optionalEntity = Optional.empty();
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDomainDAO<T> selectedDAO = (IDomainDAO<T>) daosMap.get(entityName);
            optionalEntity = selectedDAO.findByIdAndInactivatedFalse(id);
        }

        return optionalEntity;
    }

    @Override
    public Optional<T> findInactivatedById(Long id, Entity entity) {
        Optional<T> optionalEntity = Optional.empty();
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDomainDAO<T> selectedDAO = (IDomainDAO<T>) daosMap.get(entityName);
            optionalEntity = selectedDAO.findByIdAndInactivatedTrue(id);
        }

        return optionalEntity;
    }

    @Override
    public List<T> findAllActivatedBy(T entity) {
        List<T> entitiesCollection = Collections.EMPTY_LIST;
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDomainDAO<T> selectedDAO = (IDomainDAO<T>) daosMap.get(entityName);
            entitiesCollection = selectedDAO.findAllByInactivatedFalse();
        }

        return entitiesCollection;
    }

    @Override
    public List<T> findAllInactivatedBy(T entity) {
        List<T> entitiesCollection = Collections.EMPTY_LIST;
        String entityName = entity.getClass().getName();

        if (daosMap.containsKey(entityName)) {
            IDomainDAO<T> selectedDAO = (IDomainDAO<T>) daosMap.get(entityName);
            entitiesCollection = selectedDAO.findAllByInactivatedTrue();
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

    private List<T> executeFindAllBy(Entity filterEntity, IDAO<T> dao) {
        List<T> entities = Collections.EMPTY_LIST;

        if (filterEntity instanceof Customer) {
            Customer customer = (Customer) filterEntity;

            if (dao instanceof ICreditCardDAO) {
                ICreditCardDAO creditCardDAO = (ICreditCardDAO) dao;
                entities = (List<T>) creditCardDAO.findAllByCustomer(customer);
            }
            if (dao instanceof ISaleDAO) {
                ISaleDAO saleDAO = (ISaleDAO) dao;
                entities = (List<T>) saleDAO.findAllByCustomer(customer);
            }
            if (dao instanceof ICustomerVoucherDAO) {
                ICustomerVoucherDAO customerVoucherDAO = (ICustomerVoucherDAO) dao;
                entities = (List<T>) customerVoucherDAO.findCustomerVoucherByCustomer(customer);
            }
        }

        return entities;
    }
}
