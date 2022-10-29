
--
-- TOC entry 210 (class 1259 OID 48427)
-- Name: basket; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.basket (
    id character varying(255) NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 48432)
-- Name: customer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer (
    id character varying(255) NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    role character varying(255),
    basket_id character varying(255)
);


--
-- TOC entry 209 (class 1259 OID 48418)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 48439)
-- Name: invoice; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.invoice (
    id character varying(255) NOT NULL,
    sum integer NOT NULL,
    customer_id character varying(255) NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 48446)
-- Name: product; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product (
    id character varying(255) NOT NULL,
    count integer NOT NULL,
    description character varying(255),
    img_url character varying(255),
    name character varying(255),
    price integer NOT NULL,
    product_technology integer,
    product_type integer
);


--
-- TOC entry 214 (class 1259 OID 48453)
-- Name: product_basket; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_basket (
    product_id character varying(255) NOT NULL,
    basket_id character varying(255) NOT NULL
);


--
-- TOC entry 215 (class 1259 OID 48458)
-- Name: product_invoice; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_invoice (
    product_id character varying(255) NOT NULL,
    invoice_id character varying(255) NOT NULL
);


--
-- TOC entry 3345 (class 0 OID 48427)
-- Dependencies: 210
-- Data for Name: basket; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3346 (class 0 OID 48432)
-- Dependencies: 211
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3344 (class 0 OID 48418)
-- Dependencies: 209
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3347 (class 0 OID 48439)
-- Dependencies: 212
-- Data for Name: invoice; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3348 (class 0 OID 48446)
-- Dependencies: 213
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3349 (class 0 OID 48453)
-- Dependencies: 214
-- Data for Name: product_basket; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3350 (class 0 OID 48458)
-- Dependencies: 215
-- Data for Name: product_invoice; Type: TABLE DATA; Schema: public; Owner: -
--



-- Completed on 2022-10-29 03:23:40

--
-- PostgreSQL database dump complete
--

